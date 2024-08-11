package org.example.cinemaflix.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "reservation_seats")
public class ReservationSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id",referencedColumnName = "id")
    @JsonBackReference
    @ToString.Exclude
    private Reservation reservation;

    private Integer seatNumber;
}
