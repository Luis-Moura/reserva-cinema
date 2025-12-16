package com.reserva_cinema.domain.mapper;

import com.reserva_cinema.api.dto.res.ReservationResponse;
import com.reserva_cinema.domain.entity.ReservationEntity;
import com.reserva_cinema.domain.entity.ShowtimeEntity;
import com.reserva_cinema.domain.entity.UserEntity;
import com.reserva_cinema.domain.enums.ReservationStatus;

import java.util.List;

public class ReservationMapper {
    public ReservationEntity toEntity(
            ReservationStatus status,
            Integer totalPrice,
            ShowtimeEntity showtimeEntity,
            UserEntity userEntity
    ) {
        ReservationEntity entity = new ReservationEntity();
        entity.setStatus(status);
        entity.setTotalPrice(totalPrice);
        entity.setShowtime(showtimeEntity);
        entity.setUser(userEntity);

        return entity;
    }

    public ReservationResponse toResponse(ReservationEntity entity, List<Integer> seatNumbers) {
        return new ReservationResponse(
                entity.getId(),
                entity.getStatus(),
                entity.getTotalPrice(),
                entity.getShowtime().getId(),
                entity.getUser().getId(),
                seatNumbers
        );
    }
}
