package com.reserva_cinema.api.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class ShowtimeResponse {
    private UUID id;
    private String startTime;
    private String endTime;
    private Integer totalSeats;
    private Integer priceInCents;

    private UUID movieId;
    private String movieTitle;
    private String movieDescription;
}
