package com.reserva_cinema.repository;

import com.reserva_cinema.domain.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovieRepository extends JpaRepository<MovieEntity, UUID> {
    MovieEntity findByTitle(String title);
}
