package org.example.cinemaflix.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.cinemaflix.model.dto.request.MovieRequestDto;
import org.example.cinemaflix.model.dto.response.MovieResponseDto;
import org.example.cinemaflix.model.dto.response.PaymentResponseDto;
import org.example.cinemaflix.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @Operation(description = "This is list of all payments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = MovieResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping
    public Page<MovieResponseDto> getAll(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "id") String sortBy,
                                         @RequestParam(defaultValue = "asc") String sortDir) {
        return movieService.getAll(page, size, sortBy, sortDir);
    }

    @Operation(description = "This is for getting movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = MovieResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping("/info/{id}")
    public MovieResponseDto getById(@PathVariable Long id) {
        return movieService.getById(id);
    }

    @Operation(description = "This is for getting movie by category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = MovieResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping("/category/{category}")
    public List<MovieResponseDto> getByCategory(@PathVariable String category) {
        return movieService.getByCategory(category);
    }

    @Operation(description = "This is for getting movie by time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = MovieResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping("/upcoming/upcomingMovies")
    public List<MovieResponseDto> getUpcomingMovies() {
        return movieService.getUpcomingMovies();
    }

    @Operation(description = "This is for adding new movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = PaymentResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public MovieResponseDto add(@RequestBody MovieRequestDto movieRequestDto){
        return movieService.add(movieRequestDto);
    }

    @Operation(description = "This is for updating a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = PaymentResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public MovieResponseDto update(@PathVariable Long id, @RequestBody MovieRequestDto movieRequestDto) {
        return movieService.update(id, movieRequestDto);
    }

    @Operation(description = "This is for deleting a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = PaymentResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        movieService.delete(id);
    }

    @Operation(description = "This is for watching a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = PaymentResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    @PostMapping("/{movie-name}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> watchMovie(@PathVariable(value = "movie-name") String movieName, HttpServletRequest request) {
        movieService.watchMovie(movieName, request);
        return ResponseEntity.ok("Enjoy Watching!!!!");
    }
}
