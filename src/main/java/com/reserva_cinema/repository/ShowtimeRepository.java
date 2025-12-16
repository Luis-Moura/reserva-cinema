package com.reserva_cinema.repository;

import com.reserva_cinema.domain.entity.ShowtimeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ShowtimeRepository extends JpaRepository<ShowtimeEntity, UUID> {
    boolean existsByMovieIdAndStartTimeBetween(
            UUID movieId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );

    Page<ShowtimeEntity> findAll(Pageable pageable);
}
