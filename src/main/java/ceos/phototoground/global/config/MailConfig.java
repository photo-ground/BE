package ceos.phototoground.global.config;

import java.util.Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@RequiredArgsConstructor
public class MailConfig {

    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_DEBUG = "mail.smtp.debug";
    private static final String MAIL_CONNECTION_TIMEOUT = "mail.smtp.connectiontimeout";
    private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";

    // SMTP 서버
    @Value("${spring.mail.host}")
    private String host;

    // 계정
    @Value("${spring.mail.username}")
    private String username;

    // 비밀번호
    @Value("${spring.mail.password}")
    private String password;

    // 포트번호
    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean auth;

    @Value("${spring.mail.properties.mail.smtp.debug}")
    private boolean debug;

    @Value("${spring.mail.properties.mail.smtp.connectiontimeout}")
    private int connectionTimeout;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean startTlsEnable;

    @Bean
    public JavaMailSender javaMailService() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl(); // Spring Mail에서 제공하는 기본 구현체로 SMTP와 통신하기 위한 클래스 (얘로 이메일 서버와 연결하고 이메일 전송)

        // SMTP 서버 설정 (SMTP 서버 정보 저장)
        mailSender.setHost(host);   // 이메일을 보낼 SMTP 서버의 호스트 이름 (Gmail SMTP 서버는 smtp.gmail.com.)
        mailSender.setPort(port);   // SMTP 포트 번호
        mailSender.setUsername(username);   // SMTP 서버에 연결할 이메일 계정 이름
        mailSender.setPassword(password);   // SMTP 서버 인증에 필요한 앱 비밀번호

        // SMTP 속성 설정 (이메일 전송에 필요한 SMTP 관련 설정값 저장)
        Properties properties = mailSender.getJavaMailProperties();
        properties.put(MAIL_SMTP_AUTH, auth);   // SMTP 인증 여부 (일반적으로 Gmail은 인증 필요함)
        properties.put(MAIL_DEBUG, debug);   // 개발 중엔 true, 운영환경에선 false 추천
        properties.put(MAIL_CONNECTION_TIMEOUT, connectionTimeout);  // SMTP 서버와의 연결 제한 시간을 설정
        properties.put(MAIL_SMTP_STARTTLS_ENABLE, startTlsEnable);   // SMTP 서버와 통신 시 STARTTLS(암호화 전송)를 활성화

        mailSender.setJavaMailProperties(properties);
        mailSender.setDefaultEncoding("UTF-8");  //이메일 본문과 제목의 문자 인코딩을 설정

        return mailSender;
    }
}
