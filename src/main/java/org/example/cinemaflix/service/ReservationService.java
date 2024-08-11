package org.example.cinemaflix.service;

import org.example.cinemaflix.dao.entity.Payment;
import org.example.cinemaflix.dao.entity.Reservation;
import org.example.cinemaflix.model.dto.request.PaymentRequestDto;
import org.example.cinemaflix.model.dto.request.ReservationRequestDto;
import org.example.cinemaflix.model.dto.response.ReservationResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReservationService {

    boolean checkSeat(Long scheduleId,List<Integer> seatNumbers);

    ReservationResponseDto createReservation(ReservationRequestDto reservationReq);

    void addSeatToReservation(Reservation reservation,List<Integer> seatNumbers);

    void updateStatus(Payment payment,PaymentRequestDto paymentRequestDto);

    ReservationResponseDto change(Long id,ReservationRequestDto reservationRequestDto);

    Page<ReservationResponseDto> getAll(int page, int size, String sortBy, String sortDir);

    ReservationResponseDto getById(Long id);

    void delete(Long id);

}
