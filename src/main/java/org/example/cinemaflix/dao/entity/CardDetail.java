package org.example.cinemaflix.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "card_details")
@Data
public class CardDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    private Customer customer;

    private String bankAccount;

    private Integer cvv;
    private LocalDate expiryDate;

    @OneToMany(mappedBy = "cardDetail",cascade = CascadeType.ALL)
    private List<Payment> payment;
}
