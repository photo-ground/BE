package ceos.phototoground.global.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 작가 비밀번호 생성을 위한 util
public class PasswordEncoderUtil {
    public static void main(String[] args) {
        // 비밀번호 암호화 객체 생성
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // 암호화하려는 비밀번호
        String rawPassword = "password1!";

        // 비밀번호 암호화
        String encodedPassword = encoder.encode(rawPassword);

        // 결과 출력
        System.out.println("원본 비밀번호: " + rawPassword);
        System.out.println("암호화된 비밀번호: " + encodedPassword);
    }
}
