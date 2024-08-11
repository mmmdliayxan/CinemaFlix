package org.example.cinemaflix.model.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequestDto {

    @NotNull
    private Long reservationId;

    @NotNull
    private CardDetailDto cardDetailDto;
}
