package org.example.cinemaflix.mapper;

import org.example.cinemaflix.dao.entity.Customer;
import org.example.cinemaflix.dao.entity.Reservation;
import org.example.cinemaflix.model.dto.request.ReservationRequestDto;
import org.example.cinemaflix.model.dto.response.ReservationResponseDto;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class ReservationMapper {


    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer", target = "customerFullName", qualifiedByName = "mapFullName")
    @Mapping(source = "schedule.id", target = "scheduleId")
    @Mapping(source = "schedule.startTime", target = "startTimeMovie")
    @Mapping(source = "reservationDate", target = "reservationDate", qualifiedByName = "mapToDate")
    public abstract ReservationResponseDto toReservationDto(Reservation reservation);
    public abstract Reservation toReservation(@MappingTarget Reservation reservation, ReservationRequestDto reservationRequestDto);

    public abstract Reservation toReservationEntity(ReservationRequestDto reservationRequestDto);

    @Named("mapFullName")
    public String mapFullName(Customer customer) {
        return customer.getName() + " " + customer.getSurname();
    }

    @Named(value = "mapToDate")
    public String mapToDate(LocalDateTime reservationDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return reservationDate.format(formatter);
    }
}
