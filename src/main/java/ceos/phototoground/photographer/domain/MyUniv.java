package ceos.phototoground.photographer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MyUniv {

    YONSEI("연세"),
    EWHA("이화"),
    SOGANG("서강"),
    HONGIK("홍익"),
    NONE("선택안함");

    private final String name; // 한국어 이름

    /**
     * 한글 이름으로 ENUM을 찾는 정적 메서드
     */
    public static MyUniv fromKoreanName(String koreanName) {
        for (MyUniv univ : values()) {
            if (univ.getName().equals(koreanName)) {
                return univ;
            }
        }
        throw new IllegalArgumentException("Invalid university name: " + koreanName);
    }
}