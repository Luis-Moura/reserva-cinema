package com.reserva_cinema.service;

import com.reserva_cinema.api.dto.req.CreateMovie;
import com.reserva_cinema.api.dto.res.MovieResponse;
import com.reserva_cinema.config.exception.BadRequestException;
import com.reserva_cinema.domain.entity.MovieEntity;
import com.reserva_cinema.domain.mapper.MovieMapper;
import com.reserva_cinema.repository.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper = new MovieMapper();

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public MovieResponse createMovie(CreateMovie request) {
        MovieEntity existingMovie = movieRepository.findByTitle(request.title());

        if (existingMovie != null) {
            throw new BadRequestException("Movie with the same title already exists.");
        }

        MovieEntity entity = movieMapper.toEntity(request);

        entity = movieRepository.save(entity);

        return movieMapper.toResponse(entity);
    }

    public Page<MovieResponse> getAllMovies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<MovieEntity> movieEntitiesPage = movieRepository.findAll(pageable);

        return movieEntitiesPage.map(movieMapper::toResponse);
    }
}
