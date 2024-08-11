package org.example.cinemaflix.service;

import org.example.cinemaflix.model.dto.request.UserRequestDto;
import org.example.cinemaflix.model.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse getById(Long id);
    List<UserResponse> getAll();

    void update(Long id, UserRequestDto userRequestDto);

    void delete(Long id);
}
