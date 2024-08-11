package org.example.cinemaflix.service.impl;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cinemaflix.model.dto.constant.Const;
import org.example.cinemaflix.dao.entity.CardDetail;
import org.example.cinemaflix.dao.entity.Payment;
import org.example.cinemaflix.dao.entity.Reservation;
import org.example.cinemaflix.dao.entity.ReservationSeat;
import org.example.cinemaflix.mapper.CardDetailMapper;
import org.example.cinemaflix.mapper.PaymentMapper;
import org.example.cinemaflix.model.dto.exceptions.DuplicatePaymentException;
import org.example.cinemaflix.model.dto.exceptions.NotFoundException;
import org.example.cinemaflix.model.dto.request.PaymentRequestDto;
import org.example.cinemaflix.model.dto.request.Ticket;
import org.example.cinemaflix.model.dto.response.PaymentResponseDto;
import org.example.cinemaflix.dao.entity.repository.CardDetailRepository;
import org.example.cinemaflix.dao.entity.repository.PaymentRepository;
import org.example.cinemaflix.dao.entity.repository.ReservationRepository;
import org.example.cinemaflix.service.EmailService;
import org.example.cinemaflix.service.PaymentService;
import org.example.cinemaflix.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;
    private final PaymentMapper paymentMapper;
    private final CardDetailMapper cardDetailMapper;
    private final CardDetailRepository cardDetailRepository;
    private final EmailService emailService;

    @Override
    public void add(PaymentRequestDto paymentRequestDto)  {
        log.info("add method for payment is started");
        if(paymentRepository.findByReservationId(paymentRequestDto.getReservationId()).isPresent()){
            throw new DuplicatePaymentException("Payment with reservation ID " + paymentRequestDto.getReservationId() + " already exists.");
        }
        Payment payment = paymentMapper.toPayment(paymentRequestDto);
        payment.setPaymentDate(LocalDateTime.now());
        Reservation reservation = reservationRepository.findById(paymentRequestDto.getReservationId()).orElseThrow(() ->
                new NotFoundException("There isn't any reservation in this id")
        );
        CardDetail cardDetail = cardDetailMapper.toCardDetail(paymentRequestDto.getCardDetailDto());
        if(cardDetailRepository.existsByBankAccount(paymentRequestDto.getCardDetailDto().getBankAccount())){
            cardDetail= cardDetailRepository.findByBankAccount(paymentRequestDto.getCardDetailDto().getBankAccount());
        }

        cardDetail.setCustomer(reservation.getCustomer());

        payment.setCardDetail(cardDetail);
        payment.setReservation(reservation);
        Integer ticketPrice = reservation.getTicketPrice();
        Integer ticketCount = reservation.getTicketCount();
        BigInteger totalPrice = findTotalPrice(ticketPrice, ticketCount);
        payment.setTotalPrice(totalPrice);

        reservationService.updateStatus(payment,paymentRequestDto);
        paymentRepository.save(payment);
        log.info("payment for reservation is successfully done");

        List<Integer> seatNumbers = reservation.getReservationSeat().stream().map(ReservationSeat::getSeatNumber).toList();
        for (Integer seatNumber : seatNumbers) {
            setTicket(reservation, seatNumber);
            try {
                emailService.sendEmailAttachment(reservation.getCustomer(), Const.FILE_NAME);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

        }

    }

    @Override
    public Page<PaymentResponseDto> getAllPayments(int page, int size, String sortBy, String sortDir) {
        log.info("getAllPayments method is started with parameters: page={},size={},sortBy={}.sortDir={}", page, size, sortBy, sortDir);
        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sortBy));
        Page<Payment> paymentPage = paymentRepository.findAll(pageable);
        Page<PaymentResponseDto> paymentDtoPage = paymentPage.map(paymentMapper::toPaymentDto);
        log.info("getAllPayments method completed successfully, Total elements: {}, Total pages: {} ", paymentDtoPage.getTotalElements(), paymentDtoPage.getTotalPages());
        return paymentDtoPage;
    }

    @Override
    public PaymentResponseDto getById(Long id) {
        log.info("getById method is started with id {} ", id);
        Payment payment = paymentRepository.findById(id).orElseThrow(() ->
                new NotFoundException("There isn't any payment in id " + id));
        PaymentResponseDto paymentResponseDto = paymentMapper.toPaymentDto(payment);
        log.info("getById method successfully done : " + paymentResponseDto);
        return paymentResponseDto;
    }

    @Override
    public void delete(Long id) {
        log.info("delete method is started with id {} ", id);
        if(paymentRepository.existsById(id)){
            paymentRepository.updateStatus(id);
        }
        log.info("payment is successfully deleted");
    }

    public void setTicket(Reservation reservation, Integer seatNumber) {
        String fileName = Const.FILE_NAME;
        String cinemaName = reservation.getSchedule().getHall().getCinema().getName();
        String movieName = reservation.getSchedule().getMovie().getName();
        String hallName = reservation.getSchedule().getHall().getName();

        Ticket ticket = new Ticket();
        ticket.setCinemaName(cinemaName);
        ticket.setMovieName(movieName);
        ticket.setHallName(hallName);
        ticket.setSeatNumber(seatNumber);
        writeToFile(ticket, fileName, reservation);
    }

    private void writeToFile(Ticket ticket, String fileName, Reservation reservation)  {
        Random number = new Random();
        int ticketNumber = 100000 + number.nextInt(900000);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            bufferedWriter.write(ticket.getCinemaName() + " Bilet: " + ticketNumber + "\n");
            bufferedWriter.write(ticket.getMovieName() + "\n");
            bufferedWriter.write(ticket.getHallName() + "/" + reservation.getSchedule().getStartTime() + "\n");
            bufferedWriter.write("Yer: " + ticket.getSeatNumber() + "\n");
            bufferedWriter.write(reservation.getTicketPrice() + " AZN");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BigInteger findTotalPrice(Integer ticketPrice, Integer ticketCount) {
        BigInteger price = new BigInteger(String.valueOf(ticketPrice));
        BigInteger count = new BigInteger(String.valueOf(ticketCount));
        return price.multiply(count);
    }


}
