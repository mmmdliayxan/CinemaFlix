package org.example.cinemaflix.dao.entity.repository;

import jakarta.transaction.Transactional;
import org.example.cinemaflix.dao.entity.ReservationSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationSeatRepository extends JpaRepository<ReservationSeat,Long> {

    @Modifying
    @Transactional
    @Query("delete from ReservationSeat r where r.reservation.id = ?1")
    void deleteByReservationId(Long reservationId);
}
