package com.example.reservation.repository;

import com.example.reservation.model.Reservation;
import com.example.reservation.model.User;
import com.example.reservation.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(User user);
    List<Reservation> findByResource(Resource resource);
}
