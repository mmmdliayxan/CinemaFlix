package org.example.cinemaflix.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cinemaflix.dao.entity.Category;
import org.example.cinemaflix.dao.entity.Movie;
import org.example.cinemaflix.dao.entity.PaymentMovie;
import org.example.cinemaflix.dao.entity.User;
import org.example.cinemaflix.model.dto.exceptions.NotFoundException;
import org.example.cinemaflix.model.dto.exceptions.PaymentRequiredException;
import org.example.cinemaflix.jwt.impl.JwtServiceImpl;
import org.example.cinemaflix.mapper.MovieMapper;
import org.example.cinemaflix.model.dto.request.MovieRequestDto;
import org.example.cinemaflix.model.dto.response.MovieResponseDto;
import org.example.cinemaflix.dao.entity.repository.CategoryRepository;
import org.example.cinemaflix.dao.entity.repository.MovieRepository;
import org.example.cinemaflix.dao.entity.repository.PaymentMovieRepository;
import org.example.cinemaflix.dao.entity.repository.UserRepository;
import org.example.cinemaflix.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final CategoryRepository categoryRepository;
    private final JwtServiceImpl jwtService;
    private final UserRepository userRepository;
    private final PaymentMovieRepository paymentMovieRepository;

    @Override
    public Page<MovieResponseDto> getAll(int page, int size, String sortBy, String sortDir) {
        log.info("getAll method for movie is started with parameters: page={},size={},sortBy={}.sortDir={}", page, size, sortBy, sortDir);
        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sortBy));
        Page<Movie> moviesPage = movieRepository.findAll(pageable);
        Page<MovieResponseDto> responseDtoPage = moviesPage.map(movieMapper::toMovieDto);
        log.info("getAll method completed successfully, Total elements: {}, Total pages: {} ", responseDtoPage.getTotalElements(), responseDtoPage.getTotalPages());
        return responseDtoPage;
    }

    @Override
    public List<MovieResponseDto> getByCategory(String categoryName) {
        log.info("getByCategory method is started with name: {}", categoryName);
        Category category = categoryRepository.findByName(categoryName);
        List<Movie> movieList = movieRepository.findByCategory(category);
        List<MovieResponseDto> responseList = movieList.stream().map(movieMapper::toMovieDto).collect(Collectors.toList());
        log.info("getByCategory method is successfully done: {}", responseList);
        return responseList;
    }

    @Override
    public List<MovieResponseDto> getUpcomingMovies() {
        log.info("getUpcomingMovies method is started");
        List<Movie> movieList = movieRepository.findAllMovies();
        List<MovieResponseDto> responseList = movieList.stream().map(movieMapper::toMovieDto).collect(Collectors.toList());
        log.info("getUpcomingMovies method is successfully done: {}", responseList);
        return responseList;
    }

    @Override
    public void watchMovie(String movieName, HttpServletRequest request) {
        log.info("watchMovie method is started for movie:{}",movieName);
        Integer userId = (Integer) jwtService.resolveClaims(request).get("user_id");
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(()-> new NotFoundException("User is not found in this id"));
        Movie movie = movieRepository.findByName(movieName)
                .orElseThrow(()->new NotFoundException("Movie is not found with this name"));

        if(!paymentMovieRepository.existsByMovieAndUser(movie,user)){
            log.error("You can't watch movie");
            throw new PaymentRequiredException("For watching movie you must pay");
        }
        else if(paymentMovieRepository.existsByMovieAndUser(movie,user)){
            PaymentMovie paymentMovie = paymentMovieRepository.findByMovieAndUser(movie,user);
            if(paymentMovie.getStatus()==0){
              log.error("You can't watch movie");
              throw new PaymentRequiredException("For watching movie you must pay again");
            }
        }
        log.info("You can watch movie");
    }

    @Override
    public MovieResponseDto getById(Long id) {
        log.info("getById method is started with id: {}", id);
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movie is not found in this id"));
        MovieResponseDto response = movieMapper.toMovieDto(movie);
        log.info("getById method is successfully done: {}", response);
        return response;
    }

    @Override
    public MovieResponseDto add(MovieRequestDto movieRequestDto) {
        log.info("Add method for movie is started");
        Category category = categoryRepository.findByName(movieRequestDto.getCategoryName());
        Movie movie = movieMapper.toMovieEntity(movieRequestDto);
        movie.setCategory(category);
        MovieResponseDto movieResponseDto = movieMapper.toMovieDto(movieRepository.save(movie));
        log.info("movie is successfully added");
        return movieResponseDto;
    }

    @Override
    public MovieResponseDto update(Long id, MovieRequestDto movieRequestDto) {
        log.info("update method for movie is started");

        Movie movie = movieRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Movie is not found in this id"));
        movieMapper.toMovie(movie,movieRequestDto);
        Category category;
        if(movieRequestDto.getCategoryName()!=null){
            category=categoryRepository.findByName(movieRequestDto.getCategoryName());
        }
        else{
            category=movie.getCategory();
        }
        movie.setCategory(category);
        MovieResponseDto movieResponseDto = movieMapper.toMovieDto(movieRepository.save(movie));
        log.info("movie is successfully updated");
        return movieResponseDto;
    }

    @Override
    public void delete(Long id) {
        log.info("delete method for movie is started with id: {}", id);
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
        } else {
            log.error("there is not any movie in this id");
        }

        log.info("movie is successfully deleted in this id");

    }



}
