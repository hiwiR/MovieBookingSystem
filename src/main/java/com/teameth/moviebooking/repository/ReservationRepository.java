package com.teameth.moviebooking.repository;

import com.teameth.moviebooking.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByUserUserid(int userId);
}
