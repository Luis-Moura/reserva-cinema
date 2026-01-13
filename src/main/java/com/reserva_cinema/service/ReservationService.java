package com.reserva_cinema.service;

import com.reserva_cinema.api.dto.req.CreateReservation;
import com.reserva_cinema.api.dto.res.ReservationResponse;
import com.reserva_cinema.config.exception.BadRequestException;
import com.reserva_cinema.config.exception.NotFoundException;
import com.reserva_cinema.domain.entity.ReservationEntity;
import com.reserva_cinema.domain.entity.ReservationSeatEntity;
import com.reserva_cinema.domain.entity.ShowtimeEntity;
import com.reserva_cinema.domain.entity.UserEntity;
import com.reserva_cinema.domain.enums.ReservationStatus;
import com.reserva_cinema.domain.mapper.ReservationMapper;
import com.reserva_cinema.repository.ReservationRepository;
import com.reserva_cinema.repository.ReservationSeatRepository;
import com.reserva_cinema.repository.ShowtimeRepository;
import com.reserva_cinema.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationSeatRepository reservationSeatRepository;
    private final ShowtimeRepository showtimeRepository;
    private final UserRepository userRepository;
    private final ReservationMapper reservationMapper = new ReservationMapper();

    public ReservationService(
            ReservationRepository reservationRepository,
            ReservationSeatRepository reservationSeatRepository,
            ShowtimeRepository showtimeRepository,
            UserRepository userRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationSeatRepository = reservationSeatRepository;
        this.showtimeRepository = showtimeRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ReservationResponse createReservation(CreateReservation request, String userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        ShowtimeEntity showtimeEntity = showtimeRepository.findById(request.showtimeId()).
                orElseThrow(() -> new NotFoundException("Showtime not found with id: " + request.showtimeId()));

        if (request.seatNumbers().isEmpty()) {
            throw new BadRequestException("At least one seat number must be provided for the reservation.");
        }

        Set<Integer> uniqueSeats = new HashSet<>(request.seatNumbers());
        if (uniqueSeats.size() != request.seatNumbers().size()) {
            throw new BadRequestException("Duplicate seat numbers are not allowed.");
        }

        if (showtimeEntity.getStartTime().isBefore(java.time.LocalDateTime.now())) {
            throw new BadRequestException("Cannot make a reservation for a showtime that has already started.");
        }

        for (Integer seatNumber : request.seatNumbers()) {
            if (
                    seatNumber == null ||
                    seatNumber < 1 ||
                    seatNumber > showtimeEntity.getTotalSeats()
            ) {

                throw new BadRequestException(
                        "Seat number " + seatNumber + " is invalid for this showtime."
                );
            }
        }

        Integer totalPrice = showtimeEntity.getPriceInCents() * request.seatNumbers().size();

        ReservationEntity reservationEntity = reservationMapper.toEntity(
                ReservationStatus.CONFIRMED,
                totalPrice,
                showtimeEntity,
                userEntity
        );

        reservationEntity = reservationRepository.save(reservationEntity);

        try {
            for (Integer seatNumber : request.seatNumbers()) {
                reservationSeatRepository.save(
                        ReservationSeatEntity.builder()
                                .reservation(reservationEntity)
                                .showtime(showtimeEntity)
                                .seatNumber(seatNumber)
                                .build()
                );
            }

            reservationSeatRepository.flush();

        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Um ou mais assentos já foram reservados");
        }

        return reservationMapper.toResponse(reservationEntity, request.seatNumbers());
    }

    public List<Integer> getAvailableSeats(UUID showtimeId) {
        ShowtimeEntity showtime = showtimeRepository.findById(showtimeId).
                orElseThrow(() -> new NotFoundException("Showtime not found with id: " + showtimeId));

        List<Integer> reservedSeats = reservationSeatRepository.findSeatNumbersByShowtimeId(showtimeId);

        return IntStream
                .rangeClosed(1, showtime.getTotalSeats())
                .boxed()
                .filter(seat -> !reservedSeats.contains(seat))
                .toList();
    }

    public Page<ReservationResponse> getAllReservations(int page, int size, String userId) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ReservationEntity> reservations =
                reservationRepository.findAllByUserId(pageable, userId);

        return reservations.map(reservation -> {
            List<Integer> seatNumbers =
                    reservationSeatRepository
                            .findSeatNumbersByReservationId(reservation.getId());

            return new ReservationResponse(
                    reservation.getId(),
                    reservation.getStatus(),
                    reservation.getTotalPrice(),
                    reservation.getShowtime().getId(),
                    reservation.getUser().getId(),
                    seatNumbers
            );
        });
    }

    public void cancelReservation(UUID reservationId, String userId) {
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation not found with id: " + reservationId));

        ShowtimeEntity showtimeEntity = showtimeRepository.findById(reservationEntity.getShowtime().getId())
                .orElseThrow(() -> new NotFoundException("Showtime not found with id: " + reservationEntity.getShowtime().getId()));

        if (showtimeEntity.getStartTime().isBefore(java.time.LocalDateTime.now())) {
            throw new BadRequestException("Cannot cancel a reservation for a showtime that has already started.");
        }

        if (!reservationEntity.getUser().getId().equals(userId)) {
            throw new BadRequestException("You are not authorized to cancel this reservation.");
        }

        if (reservationEntity.getStatus() == ReservationStatus.CANCELLED) {
            throw new BadRequestException("Reservation is already cancelled.");
        }

        reservationEntity.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservationEntity);
    }
}
