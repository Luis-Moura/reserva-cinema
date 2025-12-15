package com.reserva_cinema.repository;

import com.reserva_cinema.domain.entity.ShowtimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShowtimeRepository extends JpaRepository<ShowtimeEntity, UUID> {
}
