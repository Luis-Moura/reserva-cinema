package com.reserva_cinema.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "reservation_Seats")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationSeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private ReservationEntity reservation;

    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;
}
