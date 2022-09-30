package com.example.training.controller;

import com.example.training.entity.Reservation;
import com.example.training.mapper.ReservationMapper;
import com.example.training.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @GetMapping()
    public ResponseEntity<List<com.example.training.dto.Reservation>> getAllReservations(){

        List<Reservation> lesReservation = reservationService.getAllReservations();

        List<com.example.training.dto.Reservation> reservations = lesReservation.parallelStream()
                .map(x-> ReservationMapper.mapToDto(x))
                .collect(Collectors.toList());

        return new ResponseEntity<List<com.example.training.dto.Reservation>>(reservations, HttpStatus.OK);
    }

    //  @GetMapping("//api/reservation/{id}")
    @GetMapping("/{id}")
    public  ResponseEntity<com.example.training.dto.Reservation> getReservation(@PathVariable int id) {
        return new ResponseEntity<com.example.training.dto.Reservation>(ReservationMapper.mapToDto(reservationService.getReservation(id)), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<com.example.training.dto.Reservation> createReservation(@RequestBody com.example.training.dto.Reservation reservation){
        return new ResponseEntity<com.example.training.dto.Reservation>(ReservationMapper.mapToDto(reservationService.createReservation(
                ReservationMapper.mapToBo(reservation))), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<com.example.training.dto.Reservation> updateReservation(@RequestBody com.example.training.dto.Reservation reservation){
        return new ResponseEntity<com.example.training.dto.Reservation>(ReservationMapper.mapToDto(reservationService.updateReservation(
                ReservationMapper.mapToBo(reservation))), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<com.example.training.dto.Reservation> deleteReservation(@PathVariable int id){
        return new ResponseEntity<com.example.training.dto.Reservation>(ReservationMapper.mapToDto(reservationService.deleteReservation(id)), HttpStatus.OK);
    }
}
