package com.teameth.moviebooking.controller;

import com.teameth.moviebooking.domain.*;
import com.teameth.moviebooking.jwt.JwtUtil;
import com.teameth.moviebooking.models.ReservationRequest;
import com.teameth.moviebooking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
public class ReservationController {
    @Autowired
    private JwtUtil jwtTokenUtil;
    @Autowired
    ReservationService reservationService;

    @Autowired
    ShowSeatService showSeatService;
    @Autowired
    HallSeatService hallSeatService;

    @Autowired
    MovieScheduleService movieScheduleService;

    @Autowired
    UserService userService;

    @RequestMapping("/reservation")
    public List<Reservation> getAllReservations(){
        return reservationService.findAllReservations();
    }
    @RequestMapping("/ListPersonalReservations")
    @PreAuthorize("hasAuthority('CUST')")

    public List<Reservation> getListOfReservationsPerUser(@RequestHeader("Authorization") String authHeader){
        String uName = jwtTokenUtil.extraUsername(authHeader.split(" ")[1]);
        User user = userService.getByUsername(uName);
        return reservationService.findReservationByUserID(user.getId());
    }
    @RequestMapping(value = "/deleteReservation/{reservationID}")
    public void deleteReservation(@PathVariable Integer reservationID) {
        reservationService.deleteUserReservation(reservationID);
    }

    @RequestMapping(method = RequestMethod.POST ,value="/movieschedule/{scheduleId}/reservation")
    public void saveNewReservation(@RequestHeader("Authorization") String authHeader,
                                   @RequestBody ReservationRequest reservationRequest, @PathVariable Integer scheduleId) throws ParseException {
        String uName = jwtTokenUtil.extraUsername(authHeader.split(" ")[1]);
        System.out.println();
        MovieSchedule movieSchedule= movieScheduleService.getMovieSchedule(scheduleId);
        Reservation reservation = new Reservation(0,
                reservationRequest.getNumberOfSeats(),
                reservationRequest.getTimeStamp(),
                reservationRequest.getStatus()
        );
        LocalDate today= LocalDate.now();
        User user = userService.getByUsername(uName);
        reservation.setUser(user);
        reservation.setMovieSchedule(movieSchedule);
        reservation.setDateStamp(Date.valueOf(today));
        Reservation savedReservation =  reservationService.save(reservation);
        String[] seats = reservationRequest.getSeatIds();
        for (String seatId : seats) {
            ShowSeat seat = new ShowSeat();
            seat.setPrice(0);
            seat.setHallSeat(hallSeatService.getHallSeat(Integer.valueOf(seatId)));
            seat.setStatus(reservationRequest.getStatus());
            seat.setMovieSchedule(movieSchedule);
            seat.setReservation(savedReservation);
            showSeatService.saveShowSeat(seat);
        }
    }
}
