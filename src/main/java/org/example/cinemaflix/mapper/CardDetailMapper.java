package org.example.cinemaflix.mapper;

import org.example.cinemaflix.dao.entity.CardDetail;
import org.example.cinemaflix.model.dto.request.CardDetailDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CardDetailMapper {

  public abstract CardDetail toCardDetail(CardDetailDto cardDetailDto);
}
