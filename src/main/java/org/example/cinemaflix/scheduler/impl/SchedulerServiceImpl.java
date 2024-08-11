package org.example.cinemaflix.scheduler.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cinemaflix.dao.entity.PaymentMovie;
import org.example.cinemaflix.dao.entity.repository.PaymentMovieRepository;
import org.example.cinemaflix.scheduler.SchedulerService;
import org.example.cinemaflix.service.PaymentMovieService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerServiceImpl implements SchedulerService {

    private final PaymentMovieService paymentMovieService;

    @Scheduled(fixedRate = 15000)
    public void checkAndUpdatePaymentStatus() {
        paymentMovieService.checkAndUpdatePaymentStatus();
    }
}
