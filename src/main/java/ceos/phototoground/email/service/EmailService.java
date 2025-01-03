package ceos.phototoground.email.service;

import ceos.phototoground.domain.customer.dto.CustomerEmailDTO;
import ceos.phototoground.domain.customer.dto.VerificationDTO;
import ceos.phototoground.email.dto.EmailDTO;
import ceos.phototoground.global.exception.CustomException;
import ceos.phototoground.global.exception.ErrorCode;
import ceos.phototoground.redis.service.RedisService;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private static final String AUTH_CODE_PREFIX = "AuthCode ";

    private final JavaMailSender mailSender;
    private final RetryTemplate retryTemplate;
    private final RedisService redisService;


    // 계정
    @Value("${spring.mail.username}")
    private String username;


    // 포그 이메일로 알림 보내기
    @Async
    public void sendEmailWithRetry(EmailDTO emailDTO, String email) {
        try {
            retryTemplate.execute(context -> {
                sendEmail(emailDTO, email); // 재시도 대상 작업
                return null; // 리턴 값이 필요 없으면 null 반환
            });
        } catch (Exception e) {
            System.err.println("Email sending failed after retries: " + e.getMessage());
        }

    }

    private void sendEmail(EmailDTO emailDTO, String targetEmail) {
        try {
            // SimpleMailMessage를 생성하여 이메일 전송
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(targetEmail); // 수신자 이메일 주소
            message.setSubject(emailDTO.getSubject()); // 이메일 제목
            message.setText(emailDTO.getText()); // 이메일 본문 텍스트

            mailSender.send(message); // 이메일 전송
            System.out.println("email sent successfully.");
        } catch (Exception e) {
            throw new CustomException(ErrorCode.EMAIL_NOT_SENT);
        }
    }

    // 인증번호 전송
    public void sendVerificationCode(CustomerEmailDTO customerEmailDTO) {

        //인증번호 생성
        String authCode = createCode();

        EmailDTO emailDTO = new EmailDTO(authCode);
        sendEmailWithRetry(emailDTO, customerEmailDTO.getEmail());

        // 이메일 인증 요청 시 인증 번호 Redis에 저장 ( key = "AuthCode " + Email / value = AuthCode )
        redisService.setValues(AUTH_CODE_PREFIX + customerEmailDTO.getEmail(),  // 재전송하면 기존 값 덮어씀
                authCode, Duration.ofMillis(600000));  //10 분
    }

    private String createCode() {

        int lenth = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < lenth; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.debug("EmailService.createCode() exception occur");
            throw new CustomException(ErrorCode.NO_SUCH_ALGORITHM);
        }
    }


    // 인증번호 인증
    public boolean verifyCode(VerificationDTO verification) {

        // 저장된 인증코드
        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + verification.getEmail());

        // Redis에 해당 메일에 대한 인증코드가 존재하는지 + 입력한 코드랑 인증코드랑 같은지 비교
        boolean authResult = redisService.checkExistsValue(redisAuthCode) && redisAuthCode.equals(
                verification.getVerificationCode());

        // 인증 완료 후 해당 key의 value값을 authCode->true 값으로 수정 (for 회원가입) : 변경할 때도 TTL 설정해줘야 적용됨
        if (authResult) {
            redisService.setValues(AUTH_CODE_PREFIX + verification.getEmail(), "true", Duration.ofMillis(600000));
        }

        return authResult;
    }

    // 인증완료 여부
    public boolean isCertified(String email) {
        return redisService.hasKey(AUTH_CODE_PREFIX + email) && "true".equals(
                redisService.getValues(AUTH_CODE_PREFIX + email));
    }
}
