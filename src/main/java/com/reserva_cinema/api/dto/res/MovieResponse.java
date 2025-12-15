package com.reserva_cinema.api.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class MovieResponse {
    private UUID id;
    private String title;
    private String genre;
    private String director;
    private String description;
    private String posterUrl;
    private Integer durationMinutes;
}
