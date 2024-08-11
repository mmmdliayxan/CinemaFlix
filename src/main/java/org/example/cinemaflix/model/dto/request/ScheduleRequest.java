package org.example.cinemaflix.model.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleRequest {

    private String movieName;
    private String cinemaName;
    private String hallName;
    private LocalDateTime startTime;

}
