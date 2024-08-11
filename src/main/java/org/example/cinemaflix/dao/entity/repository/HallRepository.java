package org.example.cinemaflix.dao.entity.repository;

import org.example.cinemaflix.dao.entity.Cinema;
import org.example.cinemaflix.dao.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HallRepository extends JpaRepository<Hall,Long> {

    List<Hall> findByCinema(Cinema cinema);

    Optional<Hall> findByNameAndCinema(String hallName, Cinema cinema);
}
