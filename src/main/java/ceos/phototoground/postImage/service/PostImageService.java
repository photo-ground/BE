package ceos.phototoground.postImage.service;

import ceos.phototoground.post.domain.Post;
import ceos.phototoground.postImage.domain.PostImage;
import ceos.phototoground.postImage.dto.PostImageResponseDTO;
import ceos.phototoground.postImage.repository.PostImageRepository;
import ceos.phototoground.spot.domain.Spot;
import ceos.phototoground.spot.service.SpotService;
import com.querydsl.core.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostImageService {

    private final PostImageRepository postImageRepository;
    private final S3ImageService s3ImageService;
    private final SpotService spotService;

    //게시글 이미지 Repository에 저장
    @Transactional
    public void save(List<PostImage> postImages) {
        postImageRepository.saveAll(postImages);
    }

    //MultipartFile->PostImage & S3에 이미지 저장
    @Transactional
    public List<PostImage> toPostImages(List<MultipartFile> files, Post post, List<Long> spotIds) {

        //프론트한테 받을 시 List<MultipartFile>가 누락되어 있으면 스프링이 자동으로 빈 리스트로 처리
        if (files.isEmpty()) {
            return new ArrayList<>();
        }

        //spotId 개수와 image 개수 안 맞으면 예외처리
        if (spotIds.size() != files.size()) {
            throw new IllegalArgumentException("이미지와 스팟 태그의 개수가 일치하지 않습니다. 모든 사진에 스팟장소 태그를 달아주세요.");
        }

        //spot id -> spot 객체
        List<Spot> spotList = spotService.findSpotsById(spotIds);

        //비동기적으로 이미지 업로드 작업 수행
        List<CompletableFuture<PostImage>> futures = new ArrayList<>();
        //파일 s3에 업로드하고 반환된 url로 postImage 객체 생성
        for (int i = 0; i < files.size(); i++) { //비동기식으로 하면 순서보장 안돼서 조회할 때 imageOrder ASC 조건 걸어서 보여주기
            int order = i + 1;
            MultipartFile image = files.get(i);
            Spot spot = spotList.get(i);

            //S3에 이미지 업로드 후 url 반환
            CompletableFuture<PostImage> future = s3ImageService.saveImage(image, "post")
                    //url 이용해 PostImage 객체 생성
                    .thenApply(url -> PostImage.builder()
                            .imageUrl(url)
                            .originalFileName(image.getOriginalFilename()) //MultipartFile 인터페이스에서 제공하는 메소드
                            .imageOrder(order)
                            .post(post)
                            .spot(spot)
                            .build());

            futures.add(future);
        }

        //모든 업로드 작업이 완료될 때까지 기다리고 실행 (List<CompletableFuture<PostImage>> -> List<PostImage>)
        CompletableFuture<List<PostImage>> allImagesFuture = CompletableFuture.allOf(
                        futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .toList());

        //CompletableFuture<List<PostImage>> -> List<PostImage>
        return allImagesFuture.join();

    }

    //게시글이미지 삭제 (deletePost에만 @Transactional 붙여 하나의 트랜잭션에 포함되게)
    public void deletePostImages(Long postId) {

        List<String> urls = postImageRepository.findImageUrlsByPost_Id(postId);
        postImageRepository.deleteByPost_Id(postId);

        // 트랜잭션 밖에서 S3 이미지 삭제하는 게 나을까,,,? (데이터베이스 작업 완료 후) 그럼 데베는 롤백됐는데 s3는 삭제되어버리는 문제 발생
        s3ImageService.deletePostImages(urls);


    }

    public List<PostImageResponseDTO> getPostImages(Long postId) {

        List<PostImage> images = postImageRepository.findByPost_Id(postId);

        List<PostImageResponseDTO> imageListDto = images.stream()
                .map(PostImageResponseDTO::from)
                .toList();

        return imageListDto;

    }

    public PostImage findImageByImageUrl(String firstImageUrl) {

        return postImageRepository.findByImageUrl(firstImageUrl);
    }

    public List<Tuple> findBySpot_Id(Long spotId, Long cursor, int size) {

        return postImageRepository.findBySpot_Id(spotId, cursor, size);
    }
}
