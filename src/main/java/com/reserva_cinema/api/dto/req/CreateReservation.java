package com.reserva_cinema.api.dto.req;

import java.util.UUID;

public record CreateReservation(
    String userId,
    UUID showtimeId
) {
}
