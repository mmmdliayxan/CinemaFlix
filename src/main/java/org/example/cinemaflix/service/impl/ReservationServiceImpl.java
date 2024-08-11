package org.example.cinemaflix.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cinemaflix.dao.entity.*;
import org.example.cinemaflix.dao.entity.repository.CustomerRepository;
import org.example.cinemaflix.dao.entity.repository.ReservationRepository;
import org.example.cinemaflix.dao.entity.repository.ReservationSeatRepository;
import org.example.cinemaflix.dao.entity.repository.ScheduleRepository;
import org.example.cinemaflix.entity.*;
import org.example.cinemaflix.model.dto.enums.ReservationStatus;
import org.example.cinemaflix.model.dto.exceptions.NotFoundException;
import org.example.cinemaflix.model.dto.exceptions.SeatCountMismatchException;
import org.example.cinemaflix.model.dto.exceptions.TimeExpiredException;
import org.example.cinemaflix.model.dto.exceptions.UnAvailableException;
import org.example.cinemaflix.mapper.CustomerMapper;
import org.example.cinemaflix.mapper.ReservationMapper;
import org.example.cinemaflix.model.dto.request.PaymentRequestDto;
import org.example.cinemaflix.model.dto.request.ReservationRequestDto;
import org.example.cinemaflix.model.dto.response.ReservationResponseDto;
import org.example.cinemaflix.repository.*;
import org.example.cinemaflix.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ScheduleRepository scheduleRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final ReservationSeatRepository seatRepository;

    @Override
    public boolean checkSeat(Long scheduleId, List<Integer> seatNumbers) {

        boolean isFlag = true;

        List<Reservation> reservations = reservationRepository.findAllByScheduleId(scheduleId);

        List<Integer> seats = reservations.stream()
                .map(Reservation::getReservationSeat)
                .flatMap(Collection::stream)
                .map(ReservationSeat::getSeatNumber)
                .toList();

        for (Integer seatNumber : seatNumbers) {
            for (Integer seat : seats) {
                if (seatNumber.equals(seat)) {
                    log.error("Seat " + seatNumber + " is already reserved");
                    isFlag = false;
                    break;
                }
            }
        }
        return isFlag;
    }

    @Override
    public ReservationResponseDto createReservation(ReservationRequestDto reservationReq) {
        log.info("createReservation method is started by customer");
        Long scheduleId = reservationReq.getScheduleId();

        Customer customer = customerMapper.toCustomer(reservationReq.getCustomerDto());
        if (customerRepository.existsByEmail(customer.getEmail())) {
            customer = customerRepository.findByEmail(customer.getEmail());
        }

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new NotFoundException("Any schedule in " + scheduleId + " isn't found"));
        List<Integer> seatNumbers = reservationReq.getSeatNumbers();

        if (!checkSeat(scheduleId, seatNumbers)) {
            throw new UnAvailableException("One or more seats are not available");
        }
        Reservation reservation = reservationMapper.toReservationEntity(reservationReq);
        reservation.setCustomer(customer);
        reservation.setSchedule(schedule);
        reservation.setTicketPrice(schedule.getTicketPrice());
        reservation.setReservationDate(LocalDateTime.now());
        if(reservation.getTicketCount()!=seatNumbers.size()){
            throw new SeatCountMismatchException("Count of seats can be neither less nor more from ticketCount");
        }
        addSeatToReservation(reservation, seatNumbers);
        ReservationResponseDto responseDto = reservationMapper.toReservationDto(reservation);
        log.info("reservation is successfully done by customer");
        return responseDto;

    }

    public void addSeatToReservation(Reservation reservation, List<Integer> seatNumbers) {
        List<ReservationSeat> reservationSeatList = new ArrayList<>();
        for (Integer seat : seatNumbers) {
            ReservationSeat reservationSeat = new ReservationSeat();
            reservationSeat.setReservation(reservation);
            reservationSeat.setSeatNumber(seat);
            reservationSeatList.add(reservationSeat);
        }
        reservation.setReservationSeat(reservationSeatList);
        reservationRepository.save(reservation);
    }

    @Override
    public void updateStatus(Payment payment, PaymentRequestDto paymentRequestDto) {
        log.info("update for status of reservation with id={} is started", paymentRequestDto.getReservationId());
        Reservation reservation = reservationRepository.findById(paymentRequestDto.getReservationId()).orElseThrow(() ->
                new NotFoundException("There isn't any reservation"));
            if (reservation.getReservationDate().isAfter(payment.getPaymentDate().minusMinutes(1))) {
                reservation.setStatus(ReservationStatus.CONFIRM);
            } else {
                reservation.setStatus(ReservationStatus.CANCELED);
                seatRepository.deleteByReservationId(reservation.getId());
                reservationRepository.save(reservation);
                throw new TimeExpiredException("Time is expired for payment please reserve ticket again!");
            }
        reservationRepository.save(reservation);
        log.info("Reservation status is successfully updated for id:{}", reservation.getId());
    }



    @Override
    public ReservationResponseDto change(Long id, ReservationRequestDto reservationRequestDto) {
        log.info("change method started for reservation id: {} ", id);

        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> {
            log.error("Reservation is not found in this id: {} ", id);
            return new NotFoundException("Reservation is not found");
        });

        seatRepository.deleteByReservationId(reservation.getId());
        reservationMapper.toReservation(reservation, reservationRequestDto);

        for (Integer seatNumber : reservationRequestDto.getSeatNumbers()) {
            ReservationSeat reservationSeat = new ReservationSeat();
            reservationSeat.setReservation(reservation);
            reservationSeat.setSeatNumber(seatNumber);
            reservation.getReservationSeat().add(reservationSeat);
        }

        reservationRepository.save(reservation);

        ReservationResponseDto reservationResponseDto = reservationMapper.toReservationDto(reservation);

        log.info("reservation successfully changed for id: {}", id);
        return reservationResponseDto;
    }

    @Override
    public Page<ReservationResponseDto> getAll(int page, int size, String sortBy, String sortDir) {
        log.info("getAll method is started with parameters: page={},size={},sortBy={}.sortDir={}", page, size, sortBy, sortDir);
        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sortBy));
        Page<Reservation> reservationPage = reservationRepository.findAll(pageable);
        Page<ReservationResponseDto> responseDtoPage = reservationPage.map(reservationMapper::toReservationDto);
        log.info("getAll method completed successfully, Total elements: {}, Total pages: {} ", responseDtoPage.getTotalElements(), responseDtoPage.getTotalPages());
        return responseDtoPage;
    }

    @Override
    public ReservationResponseDto getById(Long id) {
        log.info("getReservation method is started with id {} ", id);
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() ->
                new NotFoundException("There isn't any reservation in id " + id));
        ReservationResponseDto responseDto = reservationMapper.toReservationDto(reservation);
        log.info("getReservation method successfully done : " + responseDto);
        return responseDto;
    }

    @Override
    public void delete(Long id) {
        log.info("deleteReservation method is started with id: {}",id);
        if(reservationRepository.existsById(id)){
            Reservation reservation = reservationRepository.findById(id).get();
            reservation.setStatus(ReservationStatus.CANCELED);
            reservationRepository.save(reservation);
        }
        log.info("reservation successfully deleted");
    }

}
