package com.reserva_cinema.api.dto.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateMovie(
        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Genre is required")
        String genre,

        @NotBlank(message = "Director is required")
        String director,

        @NotBlank(message = "Description is required")
        String description,

        @NotBlank(message = "Poster URL is required")
        String posterUrl,

        @NotNull(message = "Duration in minutes is required")
        @Min(value = 1, message = "Duration must be at least 1 minute")
        Integer durationMinutes
) {
}
