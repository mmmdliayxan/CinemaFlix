package org.example.cinemaflix.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id")
    @ToString.Exclude
    private Reservation reservation;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime paymentDate;

    private BigInteger totalPrice;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_details_id",referencedColumnName = "id")
    private CardDetail cardDetail;

    @Column(nullable = false,columnDefinition = "INT DEFAULT '1'")
    private Integer status=1;
}
