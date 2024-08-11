package org.example.cinemaflix.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.cinemaflix.model.dto.request.MovieRequestDto;
import org.example.cinemaflix.model.dto.response.MovieResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MovieService {

    MovieResponseDto getById(Long id);

    Page<MovieResponseDto> getAll(int page,int size,String sortBy,String sortDir);

    List<MovieResponseDto> getByCategory(String categoryName);

    List<MovieResponseDto> getUpcomingMovies();

    void watchMovie(String movieName, HttpServletRequest request);

    MovieResponseDto add(MovieRequestDto movieRequestDto);

    MovieResponseDto update(Long id,MovieRequestDto movieRequestDto);

    void delete(Long id);



}
