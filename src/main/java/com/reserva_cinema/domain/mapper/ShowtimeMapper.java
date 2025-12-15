package com.reserva_cinema.domain.mapper;

import com.reserva_cinema.api.dto.req.CreateShowtime;
import com.reserva_cinema.api.dto.res.ShowtimeResponse;
import com.reserva_cinema.domain.entity.MovieEntity;
import com.reserva_cinema.domain.entity.ShowtimeEntity;

public class ShowtimeMapper {
    public ShowtimeEntity toEntity(CreateShowtime request, MovieEntity movieEntity) {
        ShowtimeEntity entity = new ShowtimeEntity();
        entity.setStartTime(request.startTime());
        entity.setEndTime(request.endTime());
        entity.setTotalSeats(request.totalSeats());
        entity.setPriceInCents(request.priceInCents());
        entity.setMovie(movieEntity);

        return entity;
    }

    public ShowtimeResponse toResponse(ShowtimeEntity entity) {
        return new ShowtimeResponse(
                entity.getId(),
                entity.getStartTime().toString(),
                entity.getEndTime().toString(),
                entity.getTotalSeats(),
                entity.getPriceInCents(),

                entity.getMovie().getId(),
                entity.getMovie().getTitle(),
                entity.getMovie().getDescription()
        );
    }
}
