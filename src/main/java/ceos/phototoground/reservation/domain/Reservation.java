package ceos.phototoground.reservation.domain;

import ceos.phototoground.customer.domain.Customer;
import ceos.phototoground.global.BaseTimeEntity;
import ceos.phototoground.photographer.domain.Photographer;
import ceos.phototoground.univ.domain.Univ;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reservation_id")
    private Long id;

    @NotNull
    private int bookingNum;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(length = 1024) // 최대 1024자까지 허용
    private String requirement;

    @NotNull
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
}
