package org.example.cinemaflix.model.dto.response;

import lombok.Data;
import org.example.cinemaflix.model.dto.enums.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReservationResponseDto {

    private Long customerId;
    private String customerFullName;
    private Integer ticketCount;
    private List<SeatResponse> reservationSeat;
    private Long scheduleId;
    private LocalDateTime startTimeMovie;
    private String reservationDate;
    private ReservationStatus status;

}
