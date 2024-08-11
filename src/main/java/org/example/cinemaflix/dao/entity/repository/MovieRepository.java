package org.example.cinemaflix.dao.entity.repository;

import org.example.cinemaflix.dao.entity.Category;
import org.example.cinemaflix.dao.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie,Long> {
    Optional<Movie> findByName(String name);

    List<Movie> findByCategory(Category category);

    @Query(value = "select * from cinema_database.movies m where m.id in (select s.movie_id from cinema_database.schedules s where s.start_time>now())",nativeQuery = true)
    List<Movie> findAllMovies();
}
