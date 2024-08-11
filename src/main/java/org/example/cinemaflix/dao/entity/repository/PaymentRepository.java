package org.example.cinemaflix.dao.entity.repository;

import org.example.cinemaflix.dao.entity.Payment;
import org.example.cinemaflix.dao.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findByReservation(Reservation reservation);

    @Query("update Payment p set p.status = 0 where p.id=:id")
    void updateStatus(Long id);

    Optional<Payment> findByReservationId(Long reservationId);
}
