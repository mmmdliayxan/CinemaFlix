package org.example.cinemaflix.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.cinemaflix.dao.entity.User;
import org.example.cinemaflix.model.dto.enums.UserStatus;
import org.example.cinemaflix.model.dto.exceptions.NotFoundException;
import org.example.cinemaflix.model.dto.exceptions.UserIsNotActiveException;
import org.example.cinemaflix.dao.entity.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("User is not found"));
        if(!isActive(user)){
            throw new UserIsNotActiveException("User is not active");
        }
        return user;
    }

    private boolean isActive(User user){
        return user.getUserStatus()==UserStatus.ACTIVE;
    }
}
