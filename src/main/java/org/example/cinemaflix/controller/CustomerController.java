package org.example.cinemaflix.controller;

import lombok.RequiredArgsConstructor;
import org.example.cinemaflix.model.dto.request.CustomerDto;
import org.example.cinemaflix.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public CustomerDto getById(@PathVariable Long id){
        return customerService.getById(id);
    }

    @GetMapping
    public List<CustomerDto> getAll(){
        return customerService.getAll();
    }
}
