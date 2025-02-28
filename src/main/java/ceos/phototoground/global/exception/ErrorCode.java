package ceos.phototoground.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Auth
    BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 잘못되었습니다."),
    FAIL_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "로그인에 실패했습니다. 다시 시도해주세요."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    FAIL_AUTHORIZATION(HttpStatus.FORBIDDEN, "권한이 없는 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR"),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "유효하지 않은 이메일 형식입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 유효하지 않습니다."),
    REUSED_PASSWORD(HttpStatus.BAD_REQUEST, "새 비밀번호는 기존 비밀번호와 달라야 합니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    AUTH_HEADER_MISSING(HttpStatus.BAD_REQUEST, "Authorization 헤더가 누락되었습니다."),

    // Univ
    UNIV_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이름의 대학교는 존재하지 않습니다."),

    //Spot
    SPOT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 id의 스팟은 존재하지 않습니다."),

    //PhotoProfile
    PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 작가 id의 프로필은 존재하지 않습니다."),

    //Schedule
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 작가 id의 스케줄은 존재하지 않습니다."),

    //PhotographerCalendar
    PHOTOGRAPHER_CALENDAR_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 작가 id의 작가캘린더는 존재하지 않습니다."),

    //Calendar
    CALENDAR_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 id의 캘린더는 존재하지 않습니다."),

    //Photographer
    PHOTOGRAPHER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 id의 사진작가는 존재하지 않습니다."),

    //Customer
    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 id의 고객은 존재하지 않습니다."),

    //Follow
    ALREADY_FOLLOWING(HttpStatus.CONFLICT, "이미 해당 사진작가를 팔로우하고 있습니다."),
    FOLLOW_RELATIONSHIP_NOT_FOUND(HttpStatus.NOT_FOUND, "팔로우 관계를 찾을 수 없습니다."),

    //Review
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."),
    REVIEW_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST, "리뷰가 이미 작성되었습니다."),
    REVIEW_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "이 예약에 대해 리뷰를 작성할 권한이 없습니다."),
    REVIEW_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "리뷰는 촬영 완료된 예약에만 작성할 수 있습니다."),

    //Reservation
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 id의 예약은 존재하지 않습니다."),
    NOT_VALID_TYPE_YEAR_MONTH(HttpStatus.BAD_REQUEST, "yearMonth 타입이 잘못되었습니다."),
    PAYER_ID_DIFFERENT(HttpStatus.BAD_REQUEST, "고객의 id와 요청하신 고객의 id가 다릅니다."),

    //Email
    EMAIL_NOT_SENT(HttpStatus.BAD_REQUEST, "이메일 전송이 실패하였습니다."),
    NO_SUCH_ALGORITHM(HttpStatus.INTERNAL_SERVER_ERROR, "요청한 알고리즘이 지원되지 않습니다."),
    EMAIL_NOT_CERTIFIED(HttpStatus.BAD_REQUEST, "이메일 인증을 해주세요."),

    //Post
    POST_NOT_EXIST(HttpStatus.NOT_FOUND, "해당 id의 게시글은 존재하지 않습니다."),
    NOT_POST_OWNER_PHOTOGRAPHER(HttpStatus.BAD_REQUEST, "게시글 작성자와 로그인 한 사용자가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
