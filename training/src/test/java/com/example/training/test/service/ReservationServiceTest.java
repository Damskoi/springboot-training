package com.example.training.test.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.example.training.AbstractDbUnitTest;

import com.example.training.entity.Employe;
import com.example.training.entity.Organisation;
import com.example.training.entity.Reservation;
import com.example.training.entity.Salle;
import com.example.training.exception.TrainingException;
import com.example.training.service.ReservationService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@DatabaseSetup(value = "classpath:datasets/Dataset.xml", type = DatabaseOperation.REFRESH)
@Transactional
public class ReservationServiceTest extends AbstractDbUnitTest{

    @Autowired
    ReservationService reservationService;


    @Test
    public void getAllReservation() {
        List<Reservation> reservations = reservationService.getAllReservations();

        assertNotNull(reservations);
        assertEquals(1, reservations.size());
    }

    @Test
    public void getReservation(){
        Reservation reservation = reservationService.getReservation(1);

      //  assertEquals(, reservation.getDatetime());
        assertEquals(1, reservation.getEmploye().getId());
        assertEquals(1, reservation.getSalle().getId());
    }

    @Test
    public void getReservationWithIdNotExisting_trowTrainingException(){

        assertThatThrownBy(() -> reservationService.getReservation(5))
                .isInstanceOf(TrainingException.class)
                .hasMessageContaining("La reservation n'existe pas");
    }

    @Test
    public void createReservationWithValidData(){
       // organisation
        Organisation organisation = new Organisation(1, "cvb",
                        "org", "un org");
        organisation.setCreationDate(new Date());
        organisation.setCreatedBy("toto");

        //employé
        Employe employe = new Employe(1, "mat10001", "hello", "world", new Date(), organisation);
        employe.setCreationDate(new Date());
        employe.setCreatedBy("toto");

        // salle
        Salle salle = new Salle(1, "sal102", 10);
        salle.setCreationDate(new Date());
        salle.setCreatedBy("toto");

        //reservation
        Reservation reservation = new Reservation(1, new Date(), employe, salle);
        reservation.setCreationDate(new Date());
        reservation.setCreatedBy("toto");

        // creation
        Reservation reservationCreated = reservationService.createReservation(reservation);

        //assertion
        assertEquals(reservation.getDatetime(), reservationCreated.getDatetime());
        assertEquals(reservation.getEmploye().getId(), reservationCreated.getEmploye().getId());
        assertEquals(reservation.getSalle().getId(), reservationCreated.getSalle().getId());
    }

    @Test
    public void createReservationWithNoExistingEmploye_trowTrainingException(){

        // organisation
        Organisation organisation = new Organisation(1, "cvb",
                "org", "un org");
        organisation.setCreationDate(new Date());
        organisation.setCreatedBy("toto");

        //employé
        Employe employe = new Employe(5, "mat10001", "hello", "world", new Date(), organisation);
        employe.setCreationDate(new Date());
        employe.setCreatedBy("toto");

        // salle
        Salle salle = new Salle(1, "sal102", 10);
        salle.setCreationDate(new Date());
        salle.setCreatedBy("toto");

        //reservation
        Reservation reservation = new Reservation(1, new Date(), employe, salle);
        reservation.setCreationDate(new Date());
        reservation.setCreatedBy("toto");

        assertThatThrownBy(() -> reservationService.createReservation(reservation))
                .isInstanceOf(TrainingException.class)
                .hasMessageContaining("L'employé n'existe pas");
    }

    @Test
    public void createReservationWithNoExistingSalle_trowTrainingException(){

        // organisation
        Organisation organisation = new Organisation(1, "cvb",
                "org", "un org");
        organisation.setCreationDate(new Date());
        organisation.setCreatedBy("toto");

        //employé
        Employe employe = new Employe(1, "mat10001", "hello", "world", new Date(), organisation);
        employe.setCreationDate(new Date());
        employe.setCreatedBy("toto");

        // salle
        Salle salle = new Salle(5, "sal105", 10);
        salle.setCreationDate(new Date());
        salle.setCreatedBy("toto");

        //reservation
        Reservation reservation = new Reservation(1, new Date(), employe, salle);
        reservation.setCreationDate(new Date());
        reservation.setCreatedBy("toto");

        assertThatThrownBy(() -> reservationService.createReservation(reservation))
                .isInstanceOf(TrainingException.class)
                .hasMessageContaining("La salle n'existe pas");
    }

    @Test
    public void UpdateReservationTestWithValidData() {

        // organisation
        Organisation organisation = new Organisation(1, "cvb",
                "org", "un org");
        organisation.setCreationDate(new Date());
        organisation.setCreatedBy("toto");

        //employé
        Employe employe = new Employe(2, "mat10002", "mornal", "christophe", new Date(), organisation);
        employe.setCreationDate(new Date());
        employe.setCreatedBy("toto");

        // salle
        Salle salle = new Salle(1, "sal102", 10);
        salle.setCreationDate(new Date());
        salle.setCreatedBy("toto");

        //reservation
        Reservation reservation = new Reservation(1, new Date(), employe, salle);
        reservation.setUpdateDate(new Date());
        reservation.setUpdatedBy("toto");

        // creation
        Reservation reservationUpdated = reservationService.updateReservation(reservation);

        //assertion
        assertEquals(reservation.getDatetime(), reservationUpdated.getDatetime());
        assertEquals(reservation.getEmploye().getId(), reservationUpdated.getEmploye().getId());
        assertEquals(reservation.getSalle().getId(), reservationUpdated.getSalle().getId());
        assertEquals(reservation.getUpdateDate(), reservationUpdated.getUpdateDate());
        assertEquals(reservation.getUpdatedBy(), reservation.getUpdatedBy());
    }



    @Test
    public void deleteReservationTestWithValidData() {

        /// organisation
        Organisation organisation = new Organisation(1, "cvb",
                "org", "un org");
        organisation.setCreationDate(new Date());
        organisation.setCreatedBy("toto");

        //employé
        Employe employe = new Employe(1, "mat10001", "hello", "world", new Date(), organisation);
        employe.setCreationDate(new Date());
        employe.setCreatedBy("toto");

        // salle
        Salle salle = new Salle(1, "sal102", 10);
        salle.setCreationDate(new Date());
        salle.setCreatedBy("toto");

        //reservation
        Reservation reservation = new Reservation(1, new Date(), employe, salle);
        reservation.setCreationDate(new Date());
        reservation.setCreatedBy("toto");

        // creation
        Reservation reservationCreated = reservationService.deleteReservation(reservation.getId());

        //assertion
     //   assertEquals(reservation.getDatetime(), reservationCreated.getDatetime()); // a voir!!! expected: <Mon Jun 27 15:42:22 CEST 2022> but was: <2022-01-01 10:54:30.0>
        assertEquals(reservation.getEmploye().getId(), reservationCreated.getEmploye().getId());
        assertEquals(reservation.getSalle().getId(), reservationCreated.getSalle().getId());
    }

}
