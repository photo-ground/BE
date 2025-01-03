package ceos.phototoground.domain.photographer.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MyUniv {

    YONSEI("연세"),
    EWHA("이화"),
    SOGANG("서강"),
    HONGIK("홍익"),
    NONE("선택안함");

    private final String name; // 한국어 이름

    // 한글 이름으로 ENUM을 찾는 메서드
    @JsonCreator
    public static MyUniv fromKoreanName(String koreanName) {
        for (MyUniv univ : values()) {
            if (univ.getName()
                    .equals(koreanName)) {
                return univ;
            }
        }
        throw new IllegalArgumentException("Invalid university name: " + koreanName);
    }

    // JSON 응답 시 한글 이름으로 반환
    @JsonValue
    public String getName() {
        return name;
    }
}