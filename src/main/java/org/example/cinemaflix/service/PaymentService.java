package org.example.cinemaflix.service;

import jakarta.mail.MessagingException;
import org.example.cinemaflix.model.dto.request.PaymentRequestDto;
import org.example.cinemaflix.model.dto.response.PaymentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface PaymentService {

    void add(PaymentRequestDto paymentRequestDto) throws IOException, MessagingException;

    Page<PaymentResponseDto> getAllPayments(int page, int size, String sortBy, String sortDir);

    PaymentResponseDto getById(Long id);

    void delete(Long id);
}
