package org.example.cinemaflix.dao.entity.repository;

import org.example.cinemaflix.dao.entity.Hall;
import org.example.cinemaflix.dao.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    boolean existsByHall(Hall hall);

    Schedule findByHall(Hall hall);

    @Query(value = "select * from schedules s where date_format(s.start_time,'%Y-%m-%d')=:date",nativeQuery = true)
    List<Schedule> findByStartTime(String date);

}
