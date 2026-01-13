package com.reserva_cinema.repository;

import com.reserva_cinema.domain.entity.ReservationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservationRepository extends JpaRepository<ReservationEntity, UUID> {
    Page<ReservationEntity> findAllByUserId(Pageable pageable, String userId);
}
