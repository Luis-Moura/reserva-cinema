package com.reserva_cinema.service;

import com.reserva_cinema.api.dto.req.CreateShowtime;
import com.reserva_cinema.api.dto.res.ShowtimeResponse;
import com.reserva_cinema.config.exception.BadRequestException;
import com.reserva_cinema.config.exception.NotFoundException;
import com.reserva_cinema.domain.entity.MovieEntity;
import com.reserva_cinema.domain.entity.ShowtimeEntity;
import com.reserva_cinema.domain.mapper.ShowtimeMapper;
import com.reserva_cinema.repository.MovieRepository;
import com.reserva_cinema.repository.ShowtimeRepository;
import org.springframework.stereotype.Service;

@Service
public class ShowtimeService {
    private final ShowtimeRepository showtimeRepository;
    private final ShowtimeMapper showtimeMapper = new ShowtimeMapper();
    private final MovieRepository movieRepository;

    public ShowtimeService (ShowtimeRepository showtimeRepository, MovieRepository movieRepository) {
        this.showtimeRepository = showtimeRepository;
        this.movieRepository = movieRepository;
    }

    public ShowtimeResponse createShowtime(CreateShowtime request) {
        if (showtimeRepository.existsByMovieIdAndStartTimeBetween(
                request.movieId(),
                request.startTime(),
                request.endTime())) {
            throw new BadRequestException("Showtime overlaps with an existing showtime for the same movie.");
        };

        MovieEntity movieEntity = movieRepository.findById(request.movieId()).orElseThrow(
                () -> new NotFoundException("Movie not found with id: " + request.movieId())
        );

        ShowtimeEntity showtimeEntity = showtimeMapper.toEntity(request, movieEntity);

        showtimeEntity = showtimeRepository.save(showtimeEntity);

        return showtimeMapper.toResponse(showtimeEntity);
    }
}
