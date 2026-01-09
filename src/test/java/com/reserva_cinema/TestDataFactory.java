package com.reserva_cinema;

import com.reserva_cinema.domain.entity.MovieEntity;
import com.reserva_cinema.domain.entity.ShowtimeEntity;
import com.reserva_cinema.domain.entity.UserEntity;
import com.reserva_cinema.repository.MovieRepository;
import com.reserva_cinema.repository.ShowtimeRepository;
import com.reserva_cinema.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TestDataFactory {

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    public UUID createShowtimeWith10Seats() {
        MovieEntity movie = movieRepository.save(
                MovieEntity.builder()
                        .title("Filme Teste")
                        .genre("AÇÃO")
                        .durationMinutes(120)
                        .director("Diretor Teste")
                        .description("teste")
                        .posterUrl("http://test")
                        .build()
        );

        ShowtimeEntity showtime = ShowtimeEntity.builder()
                .totalSeats(10)
                .priceInCents(2000)
                .startTime(LocalDateTime.now().plusHours(1))
                .endTime(LocalDateTime.now().plusHours(3))
                .movie(movie)
                .build();

        return showtimeRepository.save(showtime).getId();
    }

    public String createUser() {
        UserEntity user = UserEntity.builder()
                .id(UUID.randomUUID().toString())
                .email("test@test.com")
                .build();

        return userRepository.save(user).getId();
    }
}

