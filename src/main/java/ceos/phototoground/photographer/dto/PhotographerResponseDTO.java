package ceos.phototoground.photographer.dto;

import ceos.phototoground.photoProfile.domain.PhotoProfile;
import ceos.phototoground.photographer.domain.Photographer;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PhotographerResponseDTO {

    private String photographerName;
    private Long photographerId;
    private int age;
    private String gender;
    private String profileUrl;

    public static PhotographerResponseDTO of(Photographer photographer, PhotoProfile photoProfile) {
        return PhotographerResponseDTO.builder()
                .photographerName(photoProfile.getNickname())
                .photographerId(photographer.getId())
                .age(LocalDate.now().getYear() - photographer.getBornYear())
                .gender(photographer.getGender().name())  // enum->string
                .profileUrl(photoProfile.getProfileUrl())
                .build();
    }

}
