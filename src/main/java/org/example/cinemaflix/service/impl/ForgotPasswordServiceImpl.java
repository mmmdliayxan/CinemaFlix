package org.example.cinemaflix.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cinemaflix.dao.entity.ForgotPassword;
import org.example.cinemaflix.dao.entity.User;
import org.example.cinemaflix.model.dto.exceptions.InvalidTokenException;
import org.example.cinemaflix.model.dto.exceptions.NotFoundException;
import org.example.cinemaflix.model.dto.exceptions.PasswordMismatchException;
import org.example.cinemaflix.model.dto.exceptions.TimeExpiredException;
import org.example.cinemaflix.model.dto.request.ChangePassword;
import org.example.cinemaflix.model.dto.request.MailBody;
import org.example.cinemaflix.dao.entity.repository.CustomerRepository;
import org.example.cinemaflix.dao.entity.repository.ForgotPasswordRepository;
import org.example.cinemaflix.dao.entity.repository.UserRepository;
import org.example.cinemaflix.service.EmailService;
import org.example.cinemaflix.service.ForgotPasswordService;
import org.example.cinemaflix.service.TokenService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final ForgotPasswordRepository passwordRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void sendEmail(String email) {
        User user = userRepository.findByCustomer(customerRepository.findByEmail(email))
                .orElseThrow(() -> new NotFoundException("User is not found"));
        String token = tokenService.generateRandomToken();
        MailBody mailBody = MailBody.builder()
                .receiverEmail(user.getCustomer().getEmail())
                .subject("Token for forgot password request")
                .text("This is the token for your forgot password request: " + token)
                .build();
        ForgotPassword forgotPassword = ForgotPassword.builder()
                .user(user)
                .token(token)
                .expirationDate(Date.from(Instant.now().plus(Duration.ofHours(1L))))
                .build();
        passwordRepository.save(forgotPassword);
        emailService.sendSimpleMessage(mailBody);
        log.info("email sent successfully for verification");
    }

    @Override
    public void verifyToken(String token, String email) {
        User user = userRepository.findByCustomer(customerRepository.findByEmail(email))
                .orElseThrow(()-> new NotFoundException("User is not found"));
        ForgotPassword forgotPassword = passwordRepository.findByTokenAndUser(token,user)
                .orElseThrow(()-> new InvalidTokenException("Token is not valid"));

        if(forgotPassword.getExpirationDate().before(Date.from(Instant.now()))){
            passwordRepository.deleteById(forgotPassword.getId());
            log.error("Token is expired");
            throw new TimeExpiredException("Time expired for token: "+token);
        }
        log.info("Token is verified");
    }

    @Override
    public void changePasswordHandler(ChangePassword changePassword, String email) {

        if(!Objects.equals(changePassword.getPassword(),changePassword.getRepeatPassword())){
            log.error("password and repeatPassword must be the same");
            throw new PasswordMismatchException("Passwords are not matched. Please enter again!");
        }
        String encodePassword = passwordEncoder.encode(changePassword.getPassword());
        userRepository.updatePassword(encodePassword,email);
        log.info("Password is successfully changed");
    }
}
