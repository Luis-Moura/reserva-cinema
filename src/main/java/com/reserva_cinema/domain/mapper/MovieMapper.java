package com.reserva_cinema.domain.mapper;

import com.reserva_cinema.api.dto.req.CreateMovie;
import com.reserva_cinema.api.dto.res.MovieResponse;
import com.reserva_cinema.domain.entity.MovieEntity;

public class MovieMapper {
    public MovieEntity toEntity(CreateMovie request) {
        MovieEntity entity = new MovieEntity();
        entity.setTitle(request.title());
        entity.setGenre(request.genre());
        entity.setDirector(request.director());
        entity.setDescription(request.description());
        entity.setPosterUrl(request.posterUrl());
        entity.setDurationMinutes(request.durationMinutes());

        return entity;
    }

    public MovieResponse toResponse(MovieEntity entity) {
        return new MovieResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getGenre(),
                entity.getDirector(),
                entity.getDescription(),
                entity.getPosterUrl(),
                entity.getDurationMinutes()
        );
    }
}
