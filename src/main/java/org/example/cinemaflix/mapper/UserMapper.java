package org.example.cinemaflix.mapper;

import org.example.cinemaflix.dao.entity.User;
import org.example.cinemaflix.model.dto.request.UserRequestDto;
import org.example.cinemaflix.model.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    User toUser(@MappingTarget User user, UserRequestDto userRequestDto);
    User toUserEntity( UserRequestDto userRequestDto);

    @Mapping(source = "customer.name",target = "firstName")
    @Mapping(source = "customer.surname",target = "lastName")
    @Mapping(source = "customer.email",target = "email")
    @Mapping(source = "customer.phoneNumber",target = "phoneNumber")
    UserResponse toUserDto(User user);
}
