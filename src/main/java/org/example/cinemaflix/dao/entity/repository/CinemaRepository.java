package org.example.cinemaflix.dao.entity.repository;

import org.example.cinemaflix.dao.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema,Long> {
    Cinema findByName(String name);
}
