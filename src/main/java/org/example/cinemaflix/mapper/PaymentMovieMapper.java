package org.example.cinemaflix.mapper;


import org.example.cinemaflix.dao.entity.PaymentMovie;
import org.example.cinemaflix.dao.entity.User;
import org.example.cinemaflix.model.dto.request.PaymentMovieRequest;
import org.example.cinemaflix.model.dto.response.PaymentMovieResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class PaymentMovieMapper {

    @Mapping(source = "user",target = "userFullName",qualifiedByName = "mapFullName")
    @Mapping(source = "movie.name",target = "movieName")
    public abstract PaymentMovieResponse toDto(PaymentMovie paymentMovie);

    public abstract PaymentMovie toEntity(@MappingTarget PaymentMovie paymentMovie, PaymentMovieRequest paymentMovieRequest);
    public abstract PaymentMovie toPaymentEntity(PaymentMovieRequest paymentMovieRequest);

    @Named("mapFullName")
    public String mapFullName(User user) {
        return user.getCustomer().getName() + " " + user.getCustomer().getSurname();
    }
}
