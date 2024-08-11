package org.example.cinemaflix;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@RequiredArgsConstructor
public class CinemaFlixApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CinemaFlixApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) {

    }
}
