package com.reserva_cinema.api.controller;

import com.reserva_cinema.api.dto.req.CreateMovie;
import com.reserva_cinema.api.dto.res.MovieResponse;
import com.reserva_cinema.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/movie")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieResponse> createMovie(@Valid @RequestBody CreateMovie movieRequest) {
        MovieResponse createdMovieResponse = movieService.createMovie(movieRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovieResponse);
    }
}
