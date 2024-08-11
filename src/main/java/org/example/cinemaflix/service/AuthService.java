package org.example.cinemaflix.service;

import org.example.cinemaflix.model.dto.request.LoginRequest;
import org.example.cinemaflix.model.dto.request.UserRequestDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> login(LoginRequest loginRequest);
    void register(UserRequestDto userRequestDto);
}
