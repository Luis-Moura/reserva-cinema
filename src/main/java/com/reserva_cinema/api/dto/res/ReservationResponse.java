package com.reserva_cinema.api.dto.res;

import com.reserva_cinema.domain.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class ReservationResponse {
    private UUID id;
    private ReservationStatus status;
    private Integer totalPrice;
    private UUID showtimeId;
    private String userId;
    private List<Integer> seatNumbers;
}
