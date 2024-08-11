package org.example.cinemaflix.model.dto.response;

import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
public class PaymentResponseDto {

    private Long reservationId;
    private String paymentDate;
    private BigInteger totalPrice;
    private Integer status;

}
