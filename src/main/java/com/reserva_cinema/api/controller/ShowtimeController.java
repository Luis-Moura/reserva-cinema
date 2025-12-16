package com.reserva_cinema.api.controller;

import com.reserva_cinema.api.dto.req.CreateShowtime;
import com.reserva_cinema.api.dto.res.ShowtimeResponse;
import com.reserva_cinema.service.ShowtimeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @GetMapping("/public/showtimes")
    public ResponseEntity<Page<ShowtimeResponse>> getAllShowtimes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ShowtimeResponse> showtimesPage = showtimeService.getAllShowtimes(page, size);

        return ResponseEntity.ok(showtimesPage);
    }

    @GetMapping("/public/showtime/{id}")
    public ResponseEntity<ShowtimeResponse> getShowtimeById(@PathVariable("id") UUID id) {
        ShowtimeResponse showtimeResponse = showtimeService.getShowtimeById(id);
        return ResponseEntity.ok(showtimeResponse);
    }
}
