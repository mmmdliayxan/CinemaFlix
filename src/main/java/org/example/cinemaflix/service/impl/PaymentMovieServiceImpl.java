package org.example.cinemaflix.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cinemaflix.dao.entity.CardDetail;
import org.example.cinemaflix.dao.entity.Movie;
import org.example.cinemaflix.dao.entity.PaymentMovie;
import org.example.cinemaflix.dao.entity.User;
import org.example.cinemaflix.model.dto.exceptions.NotFoundException;
import org.example.cinemaflix.jwt.impl.JwtServiceImpl;
import org.example.cinemaflix.mapper.CardDetailMapper;
import org.example.cinemaflix.mapper.PaymentMovieMapper;
import org.example.cinemaflix.model.dto.request.PaymentMovieRequest;
import org.example.cinemaflix.model.dto.response.PaymentMovieResponse;
import org.example.cinemaflix.dao.entity.repository.CardDetailRepository;
import org.example.cinemaflix.dao.entity.repository.MovieRepository;
import org.example.cinemaflix.dao.entity.repository.PaymentMovieRepository;
import org.example.cinemaflix.dao.entity.repository.UserRepository;
import org.example.cinemaflix.service.PaymentMovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentMovieServiceImpl implements PaymentMovieService {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final CardDetailMapper cardDetailMapper;
    private final CardDetailRepository cardDetailRepository;
    private final PaymentMovieRepository paymentMovieRepository;
    private final PaymentMovieMapper paymentMovieMapper;
    private final JwtServiceImpl jwtService;
    private final TaskScheduler taskScheduler;

    @Override
    public void add(PaymentMovieRequest paymentMovieRequest, HttpServletRequest request) {
        log.info("add method for payment is started");
        PaymentMovie paymentMovie = paymentMovieMapper.toPaymentEntity(paymentMovieRequest);
        paymentMovie.setPaymentDate(LocalDateTime.now());
        Integer userId = (Integer)jwtService.resolveClaims(request).get("user_id");
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(()-> new NotFoundException("User is not found in this id"));
        Movie movie = movieRepository.findByName(paymentMovieRequest.getMovieName())
                .orElseThrow(()->new NotFoundException("Movie is not found with this name"));
        CardDetail cardDetail = cardDetailMapper.toCardDetail(paymentMovieRequest.getCardDetailDto());
        if(cardDetailRepository.existsByBankAccount(paymentMovieRequest.getCardDetailDto().getBankAccount())){
            cardDetail= cardDetailRepository.findByBankAccount(paymentMovieRequest.getCardDetailDto().getBankAccount());
        }
        cardDetail.setCustomer(user.getCustomer());

        paymentMovie.setMovie(movie);
        paymentMovie.setUser(user);
        paymentMovie.setCardDetail(cardDetail);
        paymentMovie.setPrice(movie.getMoviePrice());
        paymentMovieRepository.save(paymentMovie);
        log.info("payment for movie is successfully done");
    }



    @Override
    public Page<PaymentMovieResponse> getAllPayments(int page, int size, String sortBy, String sortDir) {
        log.info("getAllPayments method is started with parameters: page={},size={},sortBy={}.sortDir={}", page, size, sortBy, sortDir);
        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sortBy));
        Page<PaymentMovie> paymentPage = paymentMovieRepository.findAll(pageable);
        Page<PaymentMovieResponse> paymentDtoPage = paymentPage.map(paymentMovieMapper::toDto);
        log.info("getAllPayments method completed successfully, Total elements: {}, Total pages: {} ", paymentDtoPage.getTotalElements(), paymentDtoPage.getTotalPages());
        return paymentDtoPage;
    }

    @Override
    public PaymentMovieResponse getById(Long id) {
        log.info("getById method is started with id {} ", id);
        PaymentMovie paymentMovie = paymentMovieRepository.findById(id).orElseThrow(() ->
                new NotFoundException("There isn't any payment in id " + id));
        PaymentMovieResponse paymentResponseDto = paymentMovieMapper.toDto(paymentMovie);
        log.info("getById method successfully done : " + paymentResponseDto);
        return paymentResponseDto;
    }

    @Override
    public void delete(Long id) {
        log.info("delete method is started with id {} ", id);
        if(paymentMovieRepository.existsById(id)){
            PaymentMovie paymentMovie = paymentMovieRepository.findById(id).get();
            paymentMovie.setStatus(0);
            paymentMovieRepository.save(paymentMovie);
        }
        log.info("payment is successfully deleted");
    }

    @Override
    public void checkAndUpdatePaymentStatus() {
        log.info("checkAndUpdatePaymentStatus task is started");
        LocalDateTime now = LocalDateTime.now();
        List<PaymentMovie> paymentMovies = paymentMovieRepository.findAll();
        for (PaymentMovie paymentMovie : paymentMovies) {
            if (Duration.between(paymentMovie.getPaymentDate(), now).toSeconds() > 15) {
                paymentMovie.setStatus(0);
                paymentMovieRepository.save(paymentMovie);
                log.info("Payment status updated to 0 for paymentMovie id: {}", paymentMovie.getId());
            }
        }
        log.info("checkAndUpdatePaymentStatus task completed");
    }
}
