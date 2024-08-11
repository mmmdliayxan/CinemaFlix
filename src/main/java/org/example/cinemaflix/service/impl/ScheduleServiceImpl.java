package org.example.cinemaflix.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cinemaflix.dao.entity.Cinema;
import org.example.cinemaflix.dao.entity.Hall;
import org.example.cinemaflix.dao.entity.Movie;
import org.example.cinemaflix.dao.entity.Schedule;
import org.example.cinemaflix.model.dto.exceptions.NotFoundException;
import org.example.cinemaflix.mapper.ScheduleMapper;
import org.example.cinemaflix.model.dto.request.ScheduleRequest;
import org.example.cinemaflix.model.dto.response.ScheduleResponse;
import org.example.cinemaflix.dao.entity.repository.CinemaRepository;
import org.example.cinemaflix.dao.entity.repository.HallRepository;
import org.example.cinemaflix.dao.entity.repository.MovieRepository;
import org.example.cinemaflix.dao.entity.repository.ScheduleRepository;
import org.example.cinemaflix.service.ScheduleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final CinemaRepository cinemaRepository;
    private final HallRepository hallRepository;
    private final MovieRepository movieRepository;

    @Override
    public Page<ScheduleResponse> getAll(int page, int size, String sortBy, String sortDir) {
        log.info("getAll method for schedule is started with parameters: page={},size={},sortBy={}.sortDir={}", page, size, sortBy, sortDir);
        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sortBy));
        Page<Schedule> schedulesPage = scheduleRepository.findAll(pageable);
        Page<ScheduleResponse> responseDtoPage = schedulesPage.map(scheduleMapper::toScheduleDto);
        log.info("getAll method completed successfully, Total elements: {}, Total pages: {} ", responseDtoPage.getTotalElements(), responseDtoPage.getTotalPages());
        return responseDtoPage;
    }

    @Override
    public ScheduleResponse getById(Long id) {
        log.info("getSchedule method is started with id: {}", id);
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Schedule is not found in this id"));
        ScheduleResponse response = scheduleMapper.toScheduleDto(schedule);
        log.info("getSchedule method is successfully done: {}", response);
        return response;
    }

    @Override
    public List<ScheduleResponse> getByCinema(String cinemaName) {
        log.info("getByCinema method started with name : {}", cinemaName);
        Cinema cinema = cinemaRepository.findByName(cinemaName);
        List<Hall> hallList = hallRepository.findByCinema(cinema);
        List<Schedule> scheduleList = new ArrayList<>();
        for (Hall hall : hallList) {
            if (scheduleRepository.existsByHall(hall)) {
                scheduleList.add(scheduleRepository.findByHall(hall));
            }
        }
        List<ScheduleResponse> responseList = scheduleList.stream().map(scheduleMapper::toScheduleDto).toList();
        ;
        log.info("getByCinema method is done: {}", responseList);
        return responseList;
    }

    @Override
    public List<ScheduleResponse> getByTime(String date) {
        log.info("getByTime method is started with date: {}", date);
        List<Schedule> scheduleList = scheduleRepository.findByStartTime(date);
        List<ScheduleResponse> responseList = scheduleList.stream().map(scheduleMapper::toScheduleDto).toList();
        log.info("getByTime method is done: {}", responseList);
        return responseList;
    }

    @Override
    public ScheduleResponse add(ScheduleRequest scheduleRequest) {
        log.info("add method is started for schedule");
        Cinema cinema = cinemaRepository.findByName(scheduleRequest.getCinemaName());
        Hall hall = hallRepository.findByNameAndCinema(scheduleRequest.getHallName(), cinema)
                .orElseThrow(() -> new NotFoundException("Hall is not found in this cinema"));
        Movie movie = movieRepository.findByName(scheduleRequest.getMovieName())
                .orElseThrow(() -> new NotFoundException("Any movie is not found like this"));
        Schedule schedule = scheduleMapper.toScheduleEntity(scheduleRequest);
        Integer ticketPrice= (int) (movie.getMoviePrice() * 0.6);
        schedule.setTicketPrice(ticketPrice);
        schedule.setHall(hall);
        schedule.setMovie(movie);

        ScheduleResponse response = scheduleMapper.toScheduleDto(scheduleRepository.save(schedule));
        log.info("schedule is successfully added: " + response);
        return response;
    }

    @Override
    public ScheduleResponse update(Long id, ScheduleRequest scheduleRequest) {
        log.info("update method is started for schedule");
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Schedule is not found in this id"));
        scheduleMapper.toSchedule(schedule, scheduleRequest);
        Cinema cinema = cinemaRepository.findByName(scheduleRequest.getCinemaName());
        Hall hall;
        Movie movie;
        if (scheduleRequest.getHallName() != null && scheduleRequest.getMovieName() != null) {
            hall = hallRepository.findByNameAndCinema(scheduleRequest.getHallName(), cinema)
                    .orElseThrow(() -> new NotFoundException("Hall is not found in this cinema"));
            movie = movieRepository.findByName(scheduleRequest.getMovieName())
                    .orElseThrow(() -> new NotFoundException("Any movie is not found like this"));
        } else {
            hall = schedule.getHall();
            movie = schedule.getMovie();
        }
        schedule.setHall(hall);
        schedule.setMovie(movie);
        ScheduleResponse response = scheduleMapper.toScheduleDto(scheduleRepository.save(schedule));
        log.info("schedule is successfully updated: " + response);
        return response;
    }

    @Override
    public void delete(Long id) {
        log.info("delete method for schedule is started with id: {}", id);
        if (scheduleRepository.existsById(id)) {
            scheduleRepository.deleteById(id);
        } else {
            log.error("there is not any schedule in this id");
        }

        log.info("schedule is successfully deleted in this id");
    }
}
