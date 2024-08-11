package org.example.cinemaflix.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cinemaflix.mapper.CustomerMapper;
import org.example.cinemaflix.model.dto.request.CustomerDto;
import org.example.cinemaflix.dao.entity.repository.CustomerRepository;
import org.example.cinemaflix.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerDto getById(Long id) {
        log.info("getById method is started for customer with id:{}",id);
        CustomerDto customerDto = customerRepository.findById(id)
                .map(customerMapper::toCustomerDto)
                .orElseThrow(NullPointerException::new);
        log.info("getById method is successfully done:{}",customerDto);
        return customerDto;
    }

    @Override
    public List<CustomerDto> getAll() {
        log.info("getAll method is started for customer");
        List<CustomerDto> customerDtoList = customerRepository.findAll()
                .stream()
                .map(customerMapper::toCustomerDto)
                .toList();
        log.info("getAll method is successfully done:{}",customerDtoList);
        return customerDtoList;
    }
}
