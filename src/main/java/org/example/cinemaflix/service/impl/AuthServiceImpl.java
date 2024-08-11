package org.example.cinemaflix.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cinemaflix.model.dto.constant.Const;
import org.example.cinemaflix.dao.entity.Authority;
import org.example.cinemaflix.dao.entity.Customer;
import org.example.cinemaflix.dao.entity.User;
import org.example.cinemaflix.model.dto.exceptions.InvalidCredentialsException;
import org.example.cinemaflix.jwt.JwtService;
import org.example.cinemaflix.mapper.CustomerMapper;
import org.example.cinemaflix.mapper.UserMapper;
import org.example.cinemaflix.model.dto.request.LoginRequest;
import org.example.cinemaflix.model.dto.request.UserRequestDto;
import org.example.cinemaflix.model.dto.response.LoginResponse;
import org.example.cinemaflix.dao.entity.repository.AuthorityRepository;
import org.example.cinemaflix.dao.entity.repository.CustomerRepository;
import org.example.cinemaflix.dao.entity.repository.UserRepository;
import org.example.cinemaflix.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        try {
            log.info("authenticate method started by: {}", loginRequest.getUsername());

            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                            loginRequest.getPassword()));
            log.info("authentication details: {}", authentication);

            String username = authentication.getName();

            User user = userRepository.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new InvalidCredentialsException("Credentials are wrong"));

            String token = jwtService.issueToken(user);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, Const.BEARER + token);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUsername(username);
            loginResponse.setToken(token);

            log.info("user: {} logged in", user.getUsername());
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(loginResponse);
        }catch (BadCredentialsException e){
            log.error("Error due to {} ", e.getMessage());
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void register(UserRequestDto userRequestDto) {
        log.info("user registration is started");

        Customer customer = customerMapper.toCustomer(userRequestDto.getCustomerDto());

        if(customerRepository.existsByEmail(customer.getEmail())){
            customer=customerRepository.findByEmail(customer.getEmail());
        }
        User user = userMapper.toUserEntity(userRequestDto);
        user.setCustomer(customer);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        Authority authority = authorityRepository.findByAuthority(userRequestDto.getRole());
        Set<Authority> authorities = new HashSet<>(Set.of(authority));
        user.setAuthorities(authorities);
        userRepository.save(user);
        log.info("user is successfully registered");
    }

}
