package org.example.cinemaflix.dao.entity.repository;

import org.example.cinemaflix.dao.entity.Movie;
import org.example.cinemaflix.dao.entity.PaymentMovie;
import org.example.cinemaflix.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMovieRepository extends JpaRepository<PaymentMovie,Long> {

    boolean existsByMovieAndUser(Movie movie, User user);

    PaymentMovie findByMovieAndUser(Movie movie, User user);
}
