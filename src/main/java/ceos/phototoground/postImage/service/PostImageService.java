package ceos.phototoground.postImage.service;

import ceos.phototoground.post.domain.Post;
import ceos.phototoground.postImage.domain.PostImage;
import ceos.phototoground.postImage.repository.PostImageRepository;
import ceos.phototoground.spot.domain.Spot;
import ceos.phototoground.spot.service.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class PostImageService {

    private final PostImageRepository postImageRepository;
    private final S3ImageService s3ImageService;
    private final SpotService spotService;

    @Transactional
    public void save(List<PostImage> postImages){
        postImageRepository.saveAll(postImages);
    }


    @Transactional
    public List<PostImage> toPostImages(List<MultipartFile> files, Post post, List<Long> spotIds){

        //프론트한테 받을 시 List<MultipartFile>가 누락되어 있으면 스프링이 자동으로 빈 리스트로 처리
        if(files.isEmpty()){
            return new ArrayList<>();
        }

        //spotId 개수와 image 개수 안 맞으면 예외처리
        if(spotIds.size()!=files.size()){
            throw new IllegalArgumentException("이미지와 스팟 태그의 개수가 일치하지 않습니다. 모든 사진에 스팟장소 태그를 달아주세요.");
        }

        //spot id -> spot
        List<Spot> spotList=spotService.findSpotsById(spotIds);

        List<PostImage> postImages=new ArrayList<>();

        //파일 s3에 업로드하고 반환된 url로 postImage 객체 생성
        for(int i=0 ; i<files.size() ; i++){ //비동기식으로 하면 순서보장 안돼서 조회할 때 imageOrder ASC 조건 걸어서 보여주기
            int order = i+1;
            MultipartFile image = files.get(i);
            Spot spot = spotList.get(i);

            //S3에 이미지 업로드 후 url 반환
            String url = s3ImageService.saveImage(image, "post");

            //url 이용해 PostImage 객체 생성
            PostImage postImage = PostImage.builder()
                    .imageUrl(url)
                    .originalFileName(image.getOriginalFilename()) //MultipartFile 인터페이스에서 제공하는 메소드
                    .imageOrder(order)
                    .post(post)
                    .spot(spot)
                    .build();

            //반환할 postImages 리스트에 postImage 추가
            postImages.add(postImage);
        }

        return postImages;

    }


}
