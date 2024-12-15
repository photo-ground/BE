package ceos.phototoground.photographer.dto;

import ceos.phototoground.post.dto.ProfilePostResponseListDTO;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PhotographerBottomDTO {
    private String introduction;
    private Long score;
    private List<String> styleList;
    private ProfilePostResponseListDTO posts;

    public static PhotographerBottomDTO of(String introduction, Long score, List<String> styleList,
                                           ProfilePostResponseListDTO postList) {
        return PhotographerBottomDTO.builder()
                .introduction(introduction)
                .score(score)
                .styleList(styleList)
                .posts(postList)
                .build();
    }
}
