package ceos.phototoground.domain.postImage.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SpotPostImageList {

    private List<SpotPostImage> spotPostImageList;
    private boolean hasNext;


    public static SpotPostImageList of(List<SpotPostImage> dtos, boolean hasNext) {
        return SpotPostImageList.builder()
                .spotPostImageList(dtos)
                .hasNext(hasNext)
                .build();
    }
}
