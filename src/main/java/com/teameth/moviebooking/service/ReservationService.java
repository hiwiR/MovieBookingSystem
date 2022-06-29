package com.teameth.moviebooking.service;

import com.teameth.moviebooking.domain.MovieSchedule;
import com.teameth.moviebooking.domain.Reservation;
import com.teameth.moviebooking.domain.Role;
import com.teameth.moviebooking.domain.ShowSeat;
import com.teameth.moviebooking.repository.MovieScheduleRepository;
import com.teameth.moviebooking.repository.ReservationRepository;
import com.teameth.moviebooking.repository.ShowSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ShowSeatRepository showSeatRepository;
    @Autowired
    MovieScheduleRepository movieScheduleRepository;


    public Reservation save(Reservation reservation) {
       return reservationRepository.save(reservation);
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();

    }

    public List<Reservation> findReservationByUserID(int userId) {
        List<Reservation> reservations= reservationRepository.findByUserUserid(userId);
        List<Reservation> filteredReservations = new ArrayList<>();
        for(Reservation reservation :reservations){
            if(!(reservation.getStatus().equals("Cancelled By User"))) {
                MovieSchedule schedule = reservation.getMovieSchedule();
                if(schedule.getDate().after(Date.valueOf(LocalDate.now())) || (schedule.getDate().toLocalDate().equals(LocalDate.now())
                        &&  schedule.getStart_time().toLocalTime().compareTo(LocalTime.now()) > 0)){
                            filteredReservations.add(reservation);}
            }




            }
        return filteredReservations;
        }

    public void deleteUserReservation(Integer reservationID) {
        Reservation reservation = reservationRepository.findById(reservationID).get();
        List<ShowSeat> showSeats = showSeatRepository.findByReservationReservationId(reservationID);
        for(ShowSeat showSeat :showSeats){
            Integer toBeDeleted = showSeat.getSeat_id();
            showSeatRepository.deleteById(toBeDeleted);
        }
        reservation.setStatus("Cancelled By User");
        reservationRepository.save(reservation);
    }
}
