package org.example.cinemaflix.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cinemaflix.dao.entity.Customer;
import org.example.cinemaflix.dao.entity.User;
import org.example.cinemaflix.model.dto.enums.UserStatus;
import org.example.cinemaflix.model.dto.exceptions.NotFoundException;
import org.example.cinemaflix.mapper.CustomerMapper;
import org.example.cinemaflix.mapper.UserMapper;
import org.example.cinemaflix.model.dto.request.UserRequestDto;
import org.example.cinemaflix.model.dto.response.UserResponse;
import org.example.cinemaflix.dao.entity.repository.UserRepository;
import org.example.cinemaflix.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final CustomerMapper customerMapper;
    @Override
    public UserResponse getById(Long id) {
        log.info("getById method is started with id: {} ",id);
        UserResponse user = userRepository.findById(id).map(userMapper::toUserDto)
                .orElseThrow(()->new NotFoundException("User is not found"));
        log.info("getById method successfully done : "+user);
        return user;
    }

    @Override
    public List<UserResponse> getAll() {
        log.info("getAll method is started for users");
        List<UserResponse> userResponseList = userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .toList();
        log.info("getAll method is done for users:{}",userResponseList);
        return userResponseList;
    }

    @Override
    public void update(Long id, UserRequestDto userRequestDto) {
        log.info("update method is started for user with id:{}",id);
        User user = userRepository.findById(id).orElseThrow(()->new NotFoundException("User is not found"));
        Customer customer ;
        if(userRequestDto.getCustomerDto()!=null) {
            customer = customerMapper.toCustomer(userRequestDto.getCustomerDto());
        }
        else{
            customer=user.getCustomer();
        }
        userMapper.toUser(user,userRequestDto);
        user.setCustomer(customer);
       userRepository.save(user);
       log.info("user is successfully updated");
    }

    @Override
    public void delete(Long id) {
       log.info("delete method is started with id: {}",id);
      if(userRepository.existsById(id)){
          User user = userRepository.findById(id).get();
          user.setUserStatus(UserStatus.INACTIVE);
          userRepository.save(user);
      }
      log.info("user successfully deleted");
    }
}
