package org.example.cinemaflix.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "payment_movies")
public class PaymentMovie {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id",referencedColumnName = "id")
    private Movie movie;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime paymentDate;

    private Integer price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_details_id",referencedColumnName = "id")
    private CardDetail cardDetail;

    @Column(nullable = false,columnDefinition = "INT DEFAULT '1'")
    private Integer status=1;
}
