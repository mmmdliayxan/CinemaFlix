package org.example.cinemaflix.model.dto.request;

import lombok.Data;

@Data
public class Ticket {
    private String cinemaName;
    private String movieName;
    private String hallName;
    private Integer seatNumber;
}
