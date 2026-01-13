package com.reserva_cinema.api.controller;

import com.reserva_cinema.api.dto.req.CreateReservation;
import com.reserva_cinema.api.dto.res.ReservationResponse;
import com.reserva_cinema.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservation")
    public ResponseEntity<ReservationResponse> createReservation(
            @Valid @RequestBody CreateReservation request,
            @AuthenticationPrincipal Jwt jwt
            )
    {
        String userId = jwt.getSubject();
        ReservationResponse response = reservationService.createReservation(request, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/available-seats/{showtimeId}")
    public ResponseEntity<List<Integer>> getAvailableSeats(
            @PathVariable("showtimeId") UUID showtimeId
    ) {
        List<Integer> availableSeats = reservationService.getAvailableSeats(showtimeId);
        return ResponseEntity.ok(availableSeats);
    }

    @GetMapping("/user/reservations")
    public ResponseEntity<Page<ReservationResponse>> getUserReservation(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        String userId = jwt.getSubject();
        Page<ReservationResponse> response = reservationService.getAllReservations(page, size, userId);

        return ResponseEntity.ok(response);
    }
}
