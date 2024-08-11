package org.example.cinemaflix.model.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReservationRequestDto {

    @NotNull
    private CustomerDto customerDto;

    @NotNull
    private Long scheduleId;

    @Max(value = 10)
    private Integer ticketCount;

    private List<Integer> seatNumbers;

}
