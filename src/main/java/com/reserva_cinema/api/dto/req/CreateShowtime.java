package com.reserva_cinema.api.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateShowtime (
        LocalDateTime startTime,

        LocalDateTime endTime,

        @NotNull(message = "Total seats is required")
        Integer totalSeats,

        @NotNull(message = "Price in cents is required")
        Integer priceInCents,

        UUID movieId
) {
}
