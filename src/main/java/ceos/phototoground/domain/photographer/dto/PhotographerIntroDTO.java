package ceos.phototoground.domain.photographer.dto;

import ceos.phototoground.domain.photoProfile.entity.PhotoProfile;
import ceos.phototoground.domain.photographer.entity.Photographer;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PhotographerIntroDTO {
    private String photographerName;
    private int age;
    private String gender;
    private Long followerNum;
    private Long price;
    private Long addPrice;
    private List<String> univ; // 촬영가능 학교
    private String profileUrl;

    
    public static PhotographerIntroDTO of(Photographer photographer, PhotoProfile photoProfile,
                                          List<String> univNameList) {
        return PhotographerIntroDTO.builder()
                .photographerName(photoProfile.getNickname())
                .age(LocalDate.now().getYear() - photographer.getBornYear())
                .gender(photographer.getGender().name()) // enum -> string
                .followerNum(photoProfile.getFollowerNum())
                .price(photoProfile.getPrice())
                .addPrice(photoProfile.getAddPrice())
                .univ(univNameList)
                .profileUrl(photoProfile.getProfileUrl())
                .build();
    }
}
