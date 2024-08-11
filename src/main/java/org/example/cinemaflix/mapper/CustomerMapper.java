package org.example.cinemaflix.mapper;



import org.example.cinemaflix.dao.entity.Customer;
import org.example.cinemaflix.model.dto.request.CustomerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CustomerMapper {


    public abstract CustomerDto toCustomerDto(Customer customer);


    public abstract Customer toCustomer(CustomerDto customerDto);

}
