package com.reserva_cinema.api.controller;

import com.reserva_cinema.api.dto.req.CreateReservation;
import com.reserva_cinema.api.dto.res.ReservationResponse;
import com.reserva_cinema.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
