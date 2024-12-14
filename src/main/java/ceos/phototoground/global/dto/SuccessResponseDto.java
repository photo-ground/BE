package ceos.phototoground.global.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessResponseDto<T> { // 공통 응답 DTO
    private Integer status;
    private String message;
    private T data;

    // 성공 응답을 위한 정적 메서드
    public static <T> SuccessResponseDto<T> success(Integer status, String message, T data) {
        return SuccessResponseDto.<T>builder()
                                 .status(status)
                                 .message(message)
                                 .data(data)
                                 .build();
    }

    // 간단한 성공 응답 (data가 없는 경우)
    public static <T> SuccessResponseDto<T> successMessage(String message) {
        return success(200, message, null);
    }
}