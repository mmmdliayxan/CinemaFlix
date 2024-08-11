package org.example.cinemaflix.service;

import org.example.cinemaflix.model.dto.request.CustomerDto;

import java.util.List;

public interface CustomerService {
    CustomerDto getById(Long id);

    List<CustomerDto> getAll();
}
