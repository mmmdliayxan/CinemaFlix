package org.example.cinemaflix.controller;

import lombok.RequiredArgsConstructor;
import org.example.cinemaflix.model.dto.request.UserRequestDto;
import org.example.cinemaflix.model.dto.response.UserResponse;
import org.example.cinemaflix.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public UserResponse getById(@PathVariable Long id){
        return userService.getById(id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserResponse> getAll(){
        return userService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PutMapping("/{id}")
    public void update(@PathVariable Long id,@RequestBody UserRequestDto userRequestDto){
        userService.update(id,userRequestDto);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @DeleteMapping ("/{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }
}
