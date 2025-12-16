package com.reserva_cinema.api.controller;

import com.reserva_cinema.api.dto.req.CreateShowtime;
import com.reserva_cinema.api.dto.res.ShowtimeResponse;
import com.reserva_cinema.service.ShowtimeService;
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
public class ShowtimeController {
    private final ShowtimeService showtimeService;

    public ShowtimeController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    @PostMapping("/showtime")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ShowtimeResponse> createShowtime(@Valid @RequestBody CreateShowtime showtimeRequest) {
        ShowtimeResponse createdShowtimeResponse = showtimeService.createShowtime(showtimeRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdShowtimeResponse);
    }
}
