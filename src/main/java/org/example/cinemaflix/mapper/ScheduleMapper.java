package org.example.cinemaflix.mapper;

import org.example.cinemaflix.dao.entity.Schedule;
import org.example.cinemaflix.model.dto.request.ScheduleRequest;
import org.example.cinemaflix.model.dto.response.ScheduleResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class ScheduleMapper {

    @Mapping(source = "movie.name", target = "movieName")
    @Mapping(source = "hall.name", target = "hallName")
    @Mapping(source = "hall.cinema.name", target = "cinemaName")
    public abstract ScheduleResponse toScheduleDto(Schedule schedule);

    public abstract Schedule toSchedule(@MappingTarget Schedule schedule, ScheduleRequest scheduleRequest);
    public abstract Schedule toScheduleEntity(ScheduleRequest scheduleRequest);
}
