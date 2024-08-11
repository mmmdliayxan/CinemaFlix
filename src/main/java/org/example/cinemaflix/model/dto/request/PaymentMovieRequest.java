package org.example.cinemaflix.model.dto.request;

import lombok.Data;

@Data
public class PaymentMovieRequest {

    private String movieName;
    private CardDetailDto cardDetailDto;

}
