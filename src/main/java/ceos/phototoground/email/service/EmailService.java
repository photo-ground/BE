package ceos.phototoground.email.service;

import ceos.phototoground.email.dto.EmailDTO;
import ceos.phototoground.global.exception.CustomException;
import ceos.phototoground.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final RetryTemplate retryTemplate;
    // 계정
    @Value("${spring.mail.username}")
    private String username;


    @Async
    public void sendEmailWithRetry(EmailDTO emailDTO) {
        try {
            retryTemplate.execute(context -> {
                sendEmail(emailDTO); // 재시도 대상 작업
                return null; // 리턴 값이 필요 없으면 null 반환
            });
        } catch (Exception e) {
            System.err.println("Email sending failed after retries: " + e.getMessage());
        }

    }

    private void sendEmail(EmailDTO emailDTO) {
        try {
            // SimpleMailMessage를 생성하여 이메일 전송
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(username); // 수신자 이메일 주소
            message.setSubject(emailDTO.getSubject()); // 이메일 제목
            message.setText(emailDTO.getText()); // 이메일 본문 텍스트

            mailSender.send(message); // 이메일 전송
            System.out.println("email sent successfully.");
        } catch (Exception e) {
            throw new CustomException(ErrorCode.EMAIL_NOT_SENT);
        }
    }
}
