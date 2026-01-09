package com.reserva_cinema.api.dto.req;

import java.util.List;
import java.util.UUID;

public record CreateReservation(
    UUID showtimeId,
    List<Integer> seatNumbers
) {
}
