package ceos.phototoground.email.dto;

import ceos.phototoground.domain.customer.entity.Customer;
import ceos.phototoground.domain.photographer.entity.Photographer;
import ceos.phototoground.domain.reservation.entity.Reservation;
import lombok.Getter;

@Getter
public class EmailDTO {

    private String subject;  //제목
    private String text;  //본문

    public EmailDTO(Customer customer, Photographer photographer) {

        this.subject = "포토그라운드 예약 신청 알림";
        this.text = customer.getId() + " 번 id의 " + customer.getName() + " 고객님께서 " + photographer.getId()
                + " 번 id의 작가님께 예약 신청 하셨습니다.";
    }

    public EmailDTO(Reservation reservation) {

        this.subject = "포토그라운드 예약 취소 알림";
        this.text = reservation.getCustomer().getId() + " 번 id의 고객님께서" + reservation.getPhotographer().getId()
                + " 번 id의 작가님 예약을 취소하셨습니다. 취소된 예약 id는 " + reservation.getId() + " 번 입니다.";
    }

    public EmailDTO(String code) {

        this.subject = "[포토그라운드] 인증번호를 안내해 드립니다.";
        this.text = "[포토그라운드] 인증번호 [" + code + "]를 입력해주세요.";
    }
}
