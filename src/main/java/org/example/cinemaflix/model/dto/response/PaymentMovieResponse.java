package org.example.cinemaflix.model.dto.response;

import lombok.Data;

import java.math.BigInteger;

@Data
public class PaymentMovieResponse {

    private String userFullName;
    private String movieName;
    private String paymentDate;
    private Integer price;
    private Integer status;

}
