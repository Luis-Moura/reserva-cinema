package com.reserva_cinema;

import com.reserva_cinema.api.dto.req.CreateReservation;
import com.reserva_cinema.repository.ReservationSeatRepository;
import com.reserva_cinema.service.ReservationService;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.transaction.annotation.Propagation;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class ReservationOverbookingTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationSeatRepository reservationSeatRepository;

    @Autowired
    private TestDataFactory testDataFactory;

    private static final int THREADS = 10;

    private UUID showtimeId;
    private String userId;

    @BeforeEach
    void setup() {
        showtimeId = testDataFactory.createShowtimeWith10Seats();

        userId = testDataFactory.createUser();
    }

    @Test
    void shouldNotAllowOverbooking() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);
        CountDownLatch latch = new CountDownLatch(THREADS);

        for (int i = 0; i < THREADS; i++) {
            executor.submit(() -> {
                try {
                    reservationService.createReservation(
                            new CreateReservation(
                                    showtimeId,
                                    List.of(1)
                            ),
                            userId
                    );
                } catch (Exception ignored) {
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        long count = reservationSeatRepository
                .countByShowtimeIdAndSeatNumber(showtimeId, 1);

        assertEquals(1, count);
    }
}
