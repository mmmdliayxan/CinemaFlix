package org.example.cinemaflix.controller;

import lombok.RequiredArgsConstructor;
import org.example.cinemaflix.model.dto.request.ScheduleRequest;
import org.example.cinemaflix.model.dto.response.ScheduleResponse;
import org.example.cinemaflix.service.ScheduleService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping("/{id}")
    public ScheduleResponse getById(@PathVariable Long id){
        return scheduleService.getById(id);
    }

    @GetMapping
    public Page<ScheduleResponse> getAll(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "id") String sortBy,
                                         @RequestParam(defaultValue = "asc") String sortDir){
        return scheduleService.getAll(page,size,sortBy,sortDir);
    }

    @GetMapping("/byCinema/{cinemaName}")
    public List<ScheduleResponse> getByCinema(@PathVariable String cinemaName){
        return scheduleService.getByCinema(cinemaName);
    }

    @GetMapping("/byTime/{date}")
    public List<ScheduleResponse> getByTime(@PathVariable String date){
        return scheduleService.getByTime(date);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleResponse add(@RequestBody ScheduleRequest scheduleRequest){
        return scheduleService.add(scheduleRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleResponse update(@PathVariable Long id,@RequestBody ScheduleRequest scheduleRequest){
        return scheduleService.update(id,scheduleRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
         scheduleService.delete(id);
    }
}
