package com.reserva_cinema.api.controller;

import com.reserva_cinema.api.dto.req.CreateMovie;
import com.reserva_cinema.api.dto.res.MovieResponse;
import com.reserva_cinema.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @GetMapping("/public/movies")
    public ResponseEntity<Page<MovieResponse>> getAllMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<MovieResponse> moviesPage = movieService.getAllMovies(page, size);

        return ResponseEntity.ok(moviesPage);
    }

    @GetMapping("/public/movie/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable("id") UUID id) {
        MovieResponse movieResponse = movieService.getMovieById(id);

        return ResponseEntity.ok(movieResponse);
    }
}
