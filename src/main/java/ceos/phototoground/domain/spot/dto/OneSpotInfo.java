package ceos.phototoground.domain.spot.dto;

import ceos.phototoground.domain.postImage.dto.SpotPostImageList;
import ceos.phototoground.domain.spot.entity.Spot;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OneSpotInfo {

    private Long spotId;
    private String spotName;
    private String content;
    private String univName;
    private SpotPostImageList imageInfo;


    public static OneSpotInfo of(Spot spot, SpotPostImageList imageList) {
        return OneSpotInfo.builder()
                .spotId(spot.getId())
                .spotName(spot.getName())
                .content(spot.getContent())
                .univName(spot.getUniv().getName())
                .imageInfo(imageList)
                .build();
    }
}
