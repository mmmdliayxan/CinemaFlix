package org.example.cinemaflix.mapper;

import org.example.cinemaflix.dao.entity.Payment;
import org.example.cinemaflix.model.dto.request.PaymentRequestDto;
import org.example.cinemaflix.model.dto.response.PaymentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public abstract class PaymentMapper {


    @Mapping(source = "reservation.id",target = "reservationId")
    @Mapping(source = "paymentDate", target = "paymentDate", qualifiedByName = "mapToDate")
    public abstract PaymentResponseDto toPaymentDto(Payment payment);

    public abstract Payment toPayment(PaymentRequestDto paymentRequest);

    @Named(value = "mapToDate")
    public String mapToDate(LocalDateTime paymentDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return paymentDate.format(formatter);
    }
}
