package ceos.phototoground.post.dto;

import ceos.phototoground.photographer.domain.Photographer;
import ceos.phototoground.post.domain.Post;
import ceos.phototoground.univ.domain.Univ;
import lombok.Getter;

import java.util.List;

@Getter
public class PostRequestDTO {
    private Long univId;
    private String content;
    private List<Long> spotIds;

    //dto->entity
    public Post toEntity(Photographer photographer, Univ univ) {
        return Post.builder()
                .content(this.content)
                .photographer(photographer)
                .univ(univ)
                .build();
    }
}
