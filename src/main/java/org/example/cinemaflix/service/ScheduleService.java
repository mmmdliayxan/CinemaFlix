package org.example.cinemaflix.service;

import org.example.cinemaflix.model.dto.request.ScheduleRequest;
import org.example.cinemaflix.model.dto.response.ScheduleResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ScheduleService {

    Page<ScheduleResponse> getAll(int page, int size, String sortBy, String sortDir);

    ScheduleResponse getById(Long id);

    List<ScheduleResponse> getByCinema(String cinemaName);

    List<ScheduleResponse> getByTime(String date);

    ScheduleResponse add(ScheduleRequest scheduleRequest);
    ScheduleResponse update(Long id,ScheduleRequest scheduleRequest);
    void delete(Long id);



}
