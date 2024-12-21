package ceos.phototoground.email.dto;

import ceos.phototoground.customer.domain.Customer;
import ceos.phototoground.photographer.domain.Photographer;
import ceos.phototoground.reservation.domain.Reservation;
import lombok.Getter;

@Getter
public class EmailDTO {

    private String subject;  //제목
    private String text;  //본문

    public EmailDTO(Customer customer, Photographer photographer) {

        this.subject = "포토그라운드 예약 신청 알림";
        this.text = customer.getId() + " id의 " + customer.getName() + " 고객님께서 " + photographer.getId()
                + " id의 작가님께 예약 신청 하셨습니다.";
    }

    public EmailDTO(Reservation reservation) {

        this.subject = "포토그라운드 예약 취소 알림";
        this.text = reservation.getCustomer().getId() + " id의 고객님께서" + reservation.getPhotographer().getId()
                + " id의 작가님 예약을 취소하셨습니다.";
    }
}
