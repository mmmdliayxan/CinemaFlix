package org.example.cinemaflix.dao.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    private String email;
    private String phoneNumber;

    @Column(nullable = false,columnDefinition = "INT DEFAULT '1'")
    private Integer status=1;

    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonManagedReference
    private User user;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    @ToString.Exclude
    private List<Reservation> reservationList;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<CardDetail> cardDetails;
}
