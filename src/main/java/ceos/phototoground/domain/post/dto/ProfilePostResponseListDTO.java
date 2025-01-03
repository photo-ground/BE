package ceos.phototoground.domain.post.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ProfilePostResponseListDTO {

    private List<ProfilePostResponseDTO> profilePostResponseDTOList;
    private boolean hasNext;

    public static ProfilePostResponseListDTO of(boolean hasNext, List<ProfilePostResponseDTO> postList) {
        return ProfilePostResponseListDTO.builder()
                .profilePostResponseDTOList(postList)
                .hasNext(hasNext)
                .build();
    }
}
