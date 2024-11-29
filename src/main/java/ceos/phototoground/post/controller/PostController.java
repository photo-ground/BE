package ceos.phototoground.post.controller;

import ceos.phototoground.post.dto.PostRequestDTO;
import ceos.phototoground.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @PostMapping("/{photographerId}")
    public ResponseEntity<Map<String, String>> createPost(@RequestPart PostRequestDTO postInfo,
                                                          @RequestPart List<MultipartFile> photos,
                                                          @PathVariable Long photographerId) { //로그인 구현 후 수정

        //로그인 구현 후, 작가인지 검증하는 로직 필요할듯 (@AuthenticationPrincipal 적용 후 Role 반환)
        postService.createPost(postInfo, photos, photographerId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "게시글 생성이 완료되었습니다.");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}/{photographerId}")
    public ResponseEntity<Map<String, String>> deletePost(@PathVariable Long postId, @PathVariable Long photographerId) {
        postService.deletePost(postId, photographerId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "게시글 삭제가 완료되었습니다.");
        return ResponseEntity.ok(response);
    }

}
