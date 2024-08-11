package org.example.cinemaflix.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.example.cinemaflix.model.dto.enums.ReservationStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Data
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    @ToString.Exclude
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;


    private Integer ticketCount;
    private Integer ticketPrice;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime reservationDate;

    @OneToMany(mappedBy = "reservation",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ReservationSeat> reservationSeat;

    @OneToOne(mappedBy = "reservation",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Payment payment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,columnDefinition = "ENUM('PENDING','CANCELED','CONFIRM') DEFAULT 'PENDING'")
    private ReservationStatus status=ReservationStatus.PENDING;
}
