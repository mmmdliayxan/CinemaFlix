package org.example.cinemaflix.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.cinemaflix.model.dto.request.ReservationRequestDto;
import org.example.cinemaflix.model.dto.response.ReservationResponseDto;
import org.example.cinemaflix.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @Operation(description = "This is list of all reservations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ReservationResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<ReservationResponseDto> getAll(@RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(defaultValue = "id") String sortBy,
                                               @RequestParam(defaultValue = "asc") String sortDir) {
        return reservationService.getAll(page, size, sortBy, sortDir);
    }

    @Operation(description = "This is for getting a reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ReservationResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ReservationResponseDto getReservation(@PathVariable Long id) {
        return reservationService.getById(id);
    }

    @Operation(description = "This is for adding new reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ReservationResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PostMapping("/reserve")
    public ResponseEntity<ReservationResponseDto> add(@RequestBody ReservationRequestDto reservationRequestDto) {
        ReservationResponseDto reservationResponseDto = reservationService.createReservation(reservationRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationResponseDto);
    }

    @Operation(description = "This is for changing a reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ReservationResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PostMapping("/change/{id}")
    public ReservationResponseDto change(@PathVariable Long id, @RequestBody ReservationRequestDto reservationRequestDto) {
        return reservationService.change(id, reservationRequestDto);
    }

    @Operation(description = "This is for deleting a reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ReservationResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        reservationService.delete(id);
    }
}
