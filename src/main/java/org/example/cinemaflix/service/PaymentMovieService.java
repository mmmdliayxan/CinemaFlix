package org.example.cinemaflix.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.cinemaflix.model.dto.request.PaymentMovieRequest;
import org.example.cinemaflix.model.dto.response.PaymentMovieResponse;
import org.springframework.data.domain.Page;

public interface PaymentMovieService {

    void add(PaymentMovieRequest paymentMovieRequest, HttpServletRequest request);

    Page<PaymentMovieResponse> getAllPayments(int page, int size, String sortBy, String sortDir);

    PaymentMovieResponse getById(Long id);

    void delete(Long id);

    void checkAndUpdatePaymentStatus();

}
