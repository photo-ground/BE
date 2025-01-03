package ceos.phototoground.domain.post.dto;

import ceos.phototoground.domain.photographer.entity.Photographer;
import ceos.phototoground.domain.post.entity.Post;
import ceos.phototoground.domain.univ.entity.Univ;
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
