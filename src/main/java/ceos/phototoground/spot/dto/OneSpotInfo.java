package ceos.phototoground.spot.dto;

import ceos.phototoground.postImage.dto.SpotPostImageList;
import ceos.phototoground.spot.domain.Spot;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OneSpotInfo {

    private Long spotId;
    private String spotName;
    private String content;
    private SpotPostImageList imageInfo;


    public static OneSpotInfo of(Spot spot, SpotPostImageList imageList) {
        return OneSpotInfo.builder()
                .spotId(spot.getId())
                .spotName(spot.getName())
                .content(spot.getContent())
                .imageInfo(imageList)
                .build();
    }
}
