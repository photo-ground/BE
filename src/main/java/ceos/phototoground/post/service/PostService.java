package ceos.phototoground.post.service;

import ceos.phototoground.photographer.domain.Photographer;
import ceos.phototoground.photographer.service.PhotographerService;
import ceos.phototoground.post.domain.Post;
import ceos.phototoground.post.dto.PostRequestDTO;
import ceos.phototoground.post.repository.PostRepository;
import ceos.phototoground.postImage.domain.PostImage;
import ceos.phototoground.postImage.service.PostImageService;
import ceos.phototoground.univ.domain.Univ;
import ceos.phototoground.univ.service.UnivService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class PostService {

    private final PostRepository postRepository;
    private final PhotographerService photographerService;
    private final UnivService univService;
    private final PostImageService postImageService;

    @Transactional
    public void createPost(PostRequestDTO dto, List<MultipartFile> photos, Long photographerId){

        Photographer photographer = photographerService.findPhotographerById(photographerId);
        Univ univ = univService.findUnivById(dto.getUnivId());

        //firstImageUrl 안 넣은 상태 -> photos를 s3에 올리고나서 url 반환받은 후에 post 필드에 매핑해주기
        Post newPost=dto.toEntity(photographer, univ);

        //MultipartFile -> PostImage로 변환 + S3에 저장
        List<PostImage> postImages = postImageService.toPostImages(photos, newPost, dto.getSpotIds());

        //PostImageRepository에 PostImage 리스트 저장
        postImageService.save(postImages);


        //newPost에 첫번째 이미지의 url을 필드값에 넣어주기
        String firstImageUrl = postImages.stream()
                .filter(postImage -> postImage.getImageOrder()==1)
                .findFirst()
                .map(PostImage::getImageUrl)
                .orElseThrow(() -> new IllegalArgumentException("imageOrder가 1인 PostImage가 없습니다."));
        newPost.mapFirstImageUrl(firstImageUrl);

        postRepository.save(newPost);
    }

    @Transactional
    public void deletePost(Long postId, Long photographerId) {

        // 존재하는 게시글인지 확인 & 로그인한 유저 == 게시글작성자 검증
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 id의 게시글이 존재하지 않습니다."));
        if(!photographerId.equals(post.getPhotographer().getId())){
            throw new IllegalArgumentException("게시글 작성자와 로그인 한 사용자가 일치하지 않습니다.");
        }


        //이미지 삭제
        postImageService.deletePostImages(postId);

        //게시글 삭제
        postRepository.delete(post);
    }
}
