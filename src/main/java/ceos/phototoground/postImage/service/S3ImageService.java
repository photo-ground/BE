package ceos.phototoground.postImage.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
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
            String newFilename = dir + "/" + UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

            //upload
            try {
                ObjectMetadata metadata = getObjectMetaData(file);

                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, newFilename, file.getInputStream(),
                        metadata)
                        .clone().withCannedAcl(CannedAccessControlList.PublicRead);

                amazonS3.putObject(putObjectRequest);
            } catch (IOException e) {
                throw new RuntimeException("이미지를 s3에 업로드 하는 중에 문제 발생", e);
            }

            //url 반환
            return amazonS3.getUrl(bucketName, newFilename).toString();

        }).exceptionally(ex -> {
            // 비동기 작업 중 예외 발생한 경우를 처리
            throw new RuntimeException("S3 이미지 업로드 실패", ex);
        });

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

    private ObjectMetadata getObjectMetaData(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();

        System.out.println(file.getContentType());
        int lastDotIndex = file.getOriginalFilename().lastIndexOf(".");
        String extension = file.getOriginalFilename().substring(lastDotIndex + 1);

        //contentType을 MIME 타입으로(image/jpeg, image/png,..)
        metadata.setContentType("image/" + extension);
        metadata.setContentLength(file.getSize());

        return metadata;
    }


    //S3에서 이미지 삭제
    public void deletePostImages(List<String> urls) {

        try {

            int idx = urls.indexOf(bucketUrl);
            System.out.println(idx);

            for (int i = 0; i < urls.size(); i++) {

                String filenameWithUUID = urls.get(i).substring(idx + bucketUrl.length() + 2);

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
}