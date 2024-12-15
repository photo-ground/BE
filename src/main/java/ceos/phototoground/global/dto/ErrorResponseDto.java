package ceos.phototoground.global.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {
    private Integer status;
    private String message;
    private String error;

    // 간단한 에러 응답을 위한 정적 메서드
    public static ErrorResponseDto error(Integer status, String message, String error) {
        return ErrorResponseDto.builder()
                               .status(status)
                               .message(message)
                               .error(error)
                               .build();
    }
}
