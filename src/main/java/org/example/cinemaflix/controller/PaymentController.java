package org.example.cinemaflix.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.example.cinemaflix.model.dto.request.PaymentRequestDto;
import org.example.cinemaflix.model.dto.response.PaymentResponseDto;
import org.example.cinemaflix.service.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(description = "This is list of all payments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = PaymentResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Payment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping
    public Page<PaymentResponseDto> getAll(@RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "id") String sortBy,
                                           @RequestParam(defaultValue = "asc") String sortDir) {
        return paymentService.getAllPayments(page, size, sortBy, sortDir);
    }

    @Operation(description = "This is for getting a payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = PaymentResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Payment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PaymentResponseDto getById(@PathVariable Long id) {
        return paymentService.getById(id);
    }

    @Operation(description = "This is for adding new payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = PaymentResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PostMapping("/pay")
    public void add(@RequestBody PaymentRequestDto paymentRequestDto) {
        try {
            paymentService.add(paymentRequestDto);
        } catch (IOException | MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(description = "This is for deleting a payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = PaymentResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        paymentService.delete(id);
    }
}
