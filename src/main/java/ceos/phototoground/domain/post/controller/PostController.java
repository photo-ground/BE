package ceos.phototoground.domain.post.controller;

import ceos.phototoground.domain.customer.dto.CustomUserDetails;
import ceos.phototoground.domain.photographer.entity.Photographer;
import ceos.phototoground.domain.post.dto.PostRequestDTO;
import ceos.phototoground.domain.post.dto.PostResponseDTO;
import ceos.phototoground.domain.post.dto.PostsListResponseDTO;
import ceos.phototoground.domain.post.service.PostService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    // 게시글 생성
    @PostMapping
    public ResponseEntity<Map<String, String>> createPost(
            @RequestPart PostRequestDTO postInfo,
            @RequestPart List<MultipartFile> photos,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        // 로그인된 사용자 정보에서 Photographer 객체 가져오기
        Photographer photographer = userDetails.getPhotographer();

        // 게시글 생성 서비스 호출
        postService.createPost(postInfo, photos, photographer.getId());

        // 성공 응답 메시지 작성
        Map<String, String> response = Map.of(
                "status", "success",
                "message", "게시글 생성이 완료되었습니다."
        );

        return ResponseEntity.ok(response);
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Map<String, String>> deletePost(@PathVariable Long postId,
                                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        Photographer photographer = userDetails.getPhotographer();
        postService.deletePost(postId, photographer.getId());

        Map<String, String> response = new HashMap<>();
        response.put("message", "게시글 삭제가 완료되었습니다.");
        return ResponseEntity.ok(response);
    }


    // 게시글 상세조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> getPost(@PathVariable Long postId) {

        PostResponseDTO dto = postService.getPost(postId);

        return ResponseEntity.ok(dto);
    }


    // 특정 학교 게시글 조회
    @GetMapping
    public ResponseEntity<PostsListResponseDTO> getUnivPosts(
            @RequestParam(value = "univ", required = true) String univ,
            @RequestParam(value = "cursor", required = false) Long cursor, //맨 첫 데이터 요청할 때는 프론트측에서 안 보낼거라
            @RequestParam(value = "size", defaultValue = "15", required = false) int size) {

        PostsListResponseDTO dto = postService.getUnivPosts(univ, cursor, size);

        return ResponseEntity.ok(dto);
    }


}
