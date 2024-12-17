package ceos.phototoground.spot.dto;

import ceos.phototoground.spot.domain.Spot;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SpotInfo {

    private Long spotId;
    private String spotName;
    private Float latitude;
    private Float longitude;
    private String spotImageUrl;

    public static SpotInfo from(Spot spot) {
        return SpotInfo.builder()
                .spotId(spot.getId())
                .spotName(spot.getName())
                .latitude(spot.getLatitude())
                .longitude(spot.getLongitude())
                .spotImageUrl(spot.getSpotImageUrl())
                .build();
    }
}
