package org.example.cinemaflix.dao.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "halls")
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer capacity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cinema_id" ,referencedColumnName = "id")
    @JsonBackReference
    Cinema cinema;
}
