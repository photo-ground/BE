package ceos.phototoground.domain.postImage.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3ImageService {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    @Value("${cloud.aws.s3.bucketUrl}")
    private String bucketUrl;

    @Value("${cloud.aws.cloudfront.domainName}")
    private String cloudFrontDomain;

    @Value("${cloud.aws.cloudfront.domainUrl}")
    private String cloudFrontDomainUrl;

    //버킷 내에 폴더 만들어서 분류해서 저장하게끔 구현(작가 프로필 이미지, 게시글 구분해서) -> 인자로 dir 받음
    @Async("ImageUploadExecutor")
    public CompletableFuture<String> saveImage(MultipartFile file, String dir) {
        return CompletableFuture.supplyAsync(() -> {
            //빈 파일인지 확인
            if (file.isEmpty()) {
                return null;
            }

            //확장자 명이 올바른지 확인 (jpg, jpeg, png, gif)
            validateFileExtension(file.getOriginalFilename());

            //파일 이름에 uuid붙여 unique하게 만들어줌
            String uuid = UUID.randomUUID().toString();
            String newFilename = dir + "/" + uuid + "-" + file.getOriginalFilename();

            //upload
            try {
                //이미지 리사이징
                byte[] resizedImage = resizeImage(file, 750, 750);

                //리사이징 된 이미지를 InputStream으로 변환 (PutObjectRequest 인자로 데이터를 스트림 형태로 전달해줘야 해서)
                InputStream resizedInputStream = new ByteArrayInputStream(resizedImage);

                //ObjectMetadata metadata = getObjectMetaData(file);
                ObjectMetadata metadata = getObjectMetaData(file, resizedImage);

                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, newFilename, resizedInputStream,
                        metadata)
                        .clone().withCannedAcl(CannedAccessControlList.PublicRead);

                amazonS3.putObject(putObjectRequest);
            } catch (IOException e) {
                throw new RuntimeException("이미지를 s3에 업로드 하는 중에 문제 발생", e);
            }

            //url 반환
            try {
                String encodedOriginalFilename = URLEncoder.encode(file.getOriginalFilename(),
                        StandardCharsets.UTF_8.name());
                String newEncodedFilename = dir + "/" + uuid + "-" + encodedOriginalFilename;
                return "https://" + cloudFrontDomain + "/" + newEncodedFilename;
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("파일 이름을 인코딩 중 문제 발생", e);
            }
        }).exceptionally(ex -> {
            // 비동기 작업 중 예외 발생한 경우를 처리
            throw new RuntimeException("S3 이미지 업로드 실패", ex);
        });

    }


    //S3에서 이미지 삭제
    public void deletePostImages(List<String> urls) {

        try {
            int idx = urls.indexOf(cloudFrontDomainUrl);
            System.out.println(idx);

            for (int i = 0; i < urls.size(); i++) {

                String filenameWithUUID = urls.get(i).substring(idx + cloudFrontDomainUrl.length() + 2);

                //한글, 특수문자 깨짐 방지
                String decodedFileName = URLDecoder.decode(filenameWithUUID, StandardCharsets.UTF_8);
                System.out.println(decodedFileName);

                //존재하는지 확인하고 지우기
                if (amazonS3.doesObjectExist(bucketName, decodedFileName)) {
                    DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, decodedFileName);
                    amazonS3.deleteObject(deleteObjectRequest);
                } else {
                    System.out.println("대상없음");
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("s3에서 이미지 삭제", e);
        }
    }


    private void validateFileExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("filename이 비었습니다.");
        }

        int lastDotIndex = filename.lastIndexOf(".");
        String extension = filename.substring(lastDotIndex + 1);

        List<String> extensionList = Arrays.asList("jpg", "jpeg", "png", "gif", "JPG", "JPEG", "PNG", "GIF");

        if (!extensionList.contains(extension)) {
            throw new IllegalArgumentException("파일 확장자 명이 잘못되었습니다.");
        }
    }

    private ObjectMetadata getObjectMetaData(MultipartFile file, byte[] resizedImage) {
        ObjectMetadata metadata = new ObjectMetadata();

        System.out.println(file.getContentType());
        int lastDotIndex = file.getOriginalFilename().lastIndexOf(".");
        String extension = file.getOriginalFilename().substring(lastDotIndex + 1);

        //contentType을 MIME 타입으로(image/jpeg, image/png,..)
        metadata.setContentType("image/" + extension);
        metadata.setContentLength(resizedImage.length);

        return metadata;
    }

    private byte[] resizeImage(MultipartFile file, int targetWidth, int targetHeight) throws IOException {
        //원본 이미지 가로, 세로 픽셀
        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //리사이징 안함
        if (originalWidth <= 750 && originalHeight <= 750) {
            ImageIO.write(originalImage, "jpg", outputStream);
            return outputStream.toByteArray();
        }

        int resizedWidth = targetWidth;
        int resizedHeight = targetHeight;

        //리사이징 : width,height 중 큰 값 찾기 -> 짧은 걸 750px로 리사이징, 원본 이미지 비율 유지
        if (originalWidth >= originalHeight) {
            resizedWidth = (int) (originalWidth * 750.0 / originalHeight);
        } else {
            resizedHeight = (int) (originalHeight * 750.0 / originalWidth);
        }

        Thumbnails.of(originalImage)
                .size(resizedWidth, resizedHeight)
                .outputQuality(0.9) // 품질 낮춰 메모리 사용 감소
                .outputFormat("jpg")
                .toOutputStream(outputStream);

        return outputStream.toByteArray();
    }

}