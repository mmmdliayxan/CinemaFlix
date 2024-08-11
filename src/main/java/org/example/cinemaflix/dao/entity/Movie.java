package org.example.cinemaflix.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String name;
    private String description;

    private LocalDate releaseDate;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    @JsonBackReference
    private Category category;

    private Integer moviePrice;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "movie",fetch = FetchType.LAZY)
    private List<PaymentMovie> paymentMovie;
}
