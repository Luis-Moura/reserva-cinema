package com.reserva_cinema.repository;

import com.reserva_cinema.domain.entity.ReservationSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ReservationSeatRepository extends JpaRepository<ReservationSeatEntity, UUID> {
    long countByShowtimeIdAndSeatNumber(UUID showtimeId, Integer seatNumber);

    @Query(
    """
        select rs.seatNumber
        from ReservationSeatEntity rs
        where rs.showtime.id = :showtimeId
    """
    )
    List<Integer> findSeatNumbersByShowtimeId(UUID showtimeId);
}
