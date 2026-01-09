package com.reserva_cinema.repository;

import com.reserva_cinema.domain.entity.ReservationSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservationSeatRepository extends JpaRepository<ReservationSeatEntity, UUID> {
    long countByShowtimeIdAndSeatNumber(UUID showtimeId, Integer seatNumber);
}
