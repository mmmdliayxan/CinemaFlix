package org.example.cinemaflix.mapper;

import org.example.cinemaflix.dao.entity.Movie;
import org.example.cinemaflix.model.dto.request.MovieRequestDto;
import org.example.cinemaflix.model.dto.response.MovieResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class MovieMapper {

    @Mapping(source = "category.name",target = "categoryName")
    public abstract MovieResponseDto toMovieDto(Movie movie);

    public abstract Movie toMovie(@MappingTarget Movie movie, MovieRequestDto movieRequestDto);
    public abstract Movie toMovieEntity(MovieRequestDto movieRequestDto);
}
