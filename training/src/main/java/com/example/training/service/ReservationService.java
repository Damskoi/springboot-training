package com.example.training.service;

import com.example.training.exception.TrainingException;
import com.example.training.entity.Reservation;
import com.example.training.repository.impl.IReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ReservationService {

    @Autowired
    IReservationRepository iReservationRepository;
    //@Autowired
    @Autowired
    EmployeService employeService;
    @Autowired
    SalleService salleService;


    public List<Reservation> getAllReservations(){
        return iReservationRepository.findAll();
    }

    public Reservation getReservation(int id){
        return checkIdExist(id);
    }

    public Reservation createReservation(Reservation reservation){
        checkReservation(reservation);
        return iReservationRepository.save(reservation);
    }

    public Reservation updateReservation(Reservation reservation){
        Reservation reservationtoUpdate = checkIdExist(reservation.getId());
        checkReservation(reservation);
        reservationtoUpdate.setDatetime(reservation.getDatetime());
        reservationtoUpdate.setEmploye(reservation.getEmploye());
        reservationtoUpdate.setSalle(reservation.getSalle());
        reservationtoUpdate.setUpdateDate(reservation.getUpdateDate());
        reservationtoUpdate.setUpdatedBy(reservation.getUpdatedBy());
        return iReservationRepository.save(reservationtoUpdate);
    }
    public Reservation deleteReservation(int id){
        Reservation reservation = checkIdExist(id);
        iReservationRepository.delete(reservation);
        return reservation;
    }

    public  void checkReservation(Reservation reservation){

        //datetime
        if(reservation.getDatetime() == null)
            throw new TrainingException("La date n'est pas renseigné", HttpStatus.BAD_REQUEST);

        // employé
        if(reservation.getEmploye() == null)
            throw new TrainingException("L'employé n'est pas pas valide/null", HttpStatus.BAD_REQUEST);
        employeService.checkIdExist(reservation.getEmploye().getId());

        // Organisation
        if(reservation.getSalle() == null)
            throw new TrainingException("La salle n'est pas pas valide/null", HttpStatus.BAD_REQUEST);
        salleService.checkIDExist(reservation.getSalle().getId());
    }

    public Reservation checkIdExist(int id){
        Optional<Reservation> reservation = iReservationRepository.findById(id);
        if(reservation.isEmpty())
            throw new TrainingException("La reservation n'existe pas", HttpStatus.NOT_FOUND);
        else return reservation.get();
    }
}
