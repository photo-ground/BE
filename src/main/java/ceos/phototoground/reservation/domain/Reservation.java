package ceos.phototoground.reservation.domain;

import ceos.phototoground.customer.domain.Customer;
import ceos.phototoground.global.entity.BaseTimeEntity;
import ceos.phototoground.photographer.domain.Photographer;
import ceos.phototoground.reservation.dto.RequestReservationDTO;
import ceos.phototoground.univ.domain.Univ;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @NotNull
    private int reserveNum;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(length = 1024) // 최대 1024자까지 허용
    private String requirement;

    @Column(length = 1024) // 최대 1024자까지 허용
    private String message;

    private String chatUrl;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private int price;

    @NotNull
    private boolean reviewComplete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phtographer_id")
    private Photographer photographer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "univ_id")
    private Univ univ;


    public static Reservation createReservation(Customer customer, Photographer photographer, Univ univ,
                                                RequestReservationDTO dto) {

        return Reservation.builder()
                .reserveNum(dto.getReserveNum())
                .status(Status.PENDING)
                .requirement(dto.getRequirement())
                .date(dto.getDate())
                .startTime(dto.getStartTime())
                .price(dto.getPrice())
                .reviewComplete(false)
                .customer(customer)
                .photographer(photographer)
                .univ(univ)
                .build();
    }
}
