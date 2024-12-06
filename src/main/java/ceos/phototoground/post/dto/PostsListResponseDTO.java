package ceos.phototoground.post.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostsListResponseDTO {
    private List<PostListResponseDTO> postList;
    private boolean hasNext;

    
    public static PostsListResponseDTO of(List<PostListResponseDTO> postList, boolean hasNext) {
        return PostsListResponseDTO.builder()
                .postList(postList)
                .hasNext(hasNext)
                .build();
    }
}
