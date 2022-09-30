package com.example.training.test.controller;


import com.example.training.controller.ReservationController;
import com.example.training.entity.Employe;
import com.example.training.entity.Organisation;
import com.example.training.entity.Reservation;
import com.example.training.entity.Salle;
import com.example.training.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getAllReservationsTest() throws Exception{
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

        //reservation0
        Reservation reservation0 = new Reservation(1, new Date(), employe, salle);
        reservation0.setCreationDate(new Date());
        reservation0.setCreatedBy("toto");

        //reservation1
        Reservation reservation1 = new Reservation(2, new Date(), employe, salle);
        reservation1.setCreationDate(new Date());
        reservation1.setCreatedBy("toto");

        ArrayList<Reservation> reservations = new ArrayList<Reservation>();
        reservations.add(reservation0);
        reservations.add(reservation1);

        when(reservationService.getAllReservations()).thenReturn(
                reservations);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/reservation")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                // assert nb reservation
                .andExpect(jsonPath("$", hasSize(2)))
                // assert reservations
                .andExpect(jsonPath("$[0].id", is(reservation0.getId())))
                .andExpect(jsonPath("$[1].id", is(reservation1.getId())))
                //.andExpect(jsonPath("$.datetime", is(reservation.getDatetime())))
                //  .andExpect(jsonPath("$.employe", is(reservation.getEmploye())))
                //  .andExpect(jsonPath("$.salle", is(reservation.getSalle())))
                .andReturn();
    }

    @Test
    public void getReservationTest() throws Exception{
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

        when(reservationService.getReservation(reservation.getId())).thenReturn(
                reservation);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/reservation/{id}", reservation.getId())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                // assert reservation
                .andExpect(jsonPath("$.id", is(reservation.getId())))
                //.andExpect(jsonPath("$.datetime", is(reservation.getDatetime())))
              //  .andExpect(jsonPath("$.employe", is(reservation.getEmploye())))
              //  .andExpect(jsonPath("$.salle", is(reservation.getSalle())))
                .andReturn();
    }

    @Test
    public void createReservationTest() throws Exception{
        // organisation
        Organisation organisation = new Organisation();
        organisation.setId(1);
        organisation.setCreationDate(new Date());
        organisation.setCreatedBy("toto");

        com.example.training.dto.Organisation organisationDto = new com.example.training.dto.Organisation();
        organisationDto.setId(organisation.getId());
        organisationDto.setOrg(organisation.getOrg());
        organisationDto.setNom(organisation.getNom());
        organisationDto.setDescription(organisation.getDescription());

         // salle
         Salle salle = new Salle(1, "sal1", 5);
         salle.setCreationDate(new Date());
         salle.setCreatedBy("toto");

         com.example.training.dto.Salle salleDto = new com.example.training.dto.Salle();
         salleDto.setId(salle.getId());
         salleDto.setNom(salle.getNom());
         salleDto.setCapacite(salle.getCapacite());

         // employe
         Employe employe  = new Employe(1, "mat1001",
                 "durand", "olivier"
                 , new Date(), organisation);
         employe.setCreationDate(new Date());
         employe.setCreatedBy("toto");

         com.example.training.dto.Employe employeDto = new com.example.training.dto.Employe();
         employeDto.setId(employe.getId());
         employeDto.setMatricule(employe.getMatricule());
         employeDto.setNom(employe.getNom());
         employeDto.setPrenom(employe.getPrenom());
         employeDto.setDateEmbauche(employe.getDateEmbauche());
         employeDto.setOrganisation(organisationDto);

         // reservation
         Reservation reservation = new Reservation(1, new Date(), employe, salle);
         reservation.setCreationDate(new Date());
         reservation.setCreatedBy("toto");

         com.example.training.dto.Reservation reservationDto= new com.example.training.dto.Reservation();
         reservationDto.setId(reservation.getId());
         reservationDto.setDatetime(reservation.getDatetime());
         reservationDto.setEmploye(employeDto);
         reservationDto.setSalle(salleDto);

         when(reservationService.createReservation(any()))
                 .thenReturn(reservation);

         RequestBuilder request = MockMvcRequestBuilders
                 .post("/api/reservation")
                 .accept(MediaType.APPLICATION_JSON)
                 .content(asJsonString(reservationDto))
                 .contentType(MediaType.APPLICATION_JSON);

         MvcResult result = mockMvc.perform(request)
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.id", is(reservation.getId())))
                 .andReturn();
    }

    @Test
    public void updateReservationTest() throws Exception{
        // organisation
        Organisation organisation = new Organisation();
        organisation.setId(1);
        organisation.setCreationDate(new Date());
        organisation.setCreatedBy("toto");

        com.example.training.dto.Organisation organisationDto = new com.example.training.dto.Organisation();
        organisationDto.setId(organisation.getId());
        organisationDto.setOrg(organisation.getOrg());
        organisationDto.setNom(organisation.getNom());
        organisationDto.setDescription(organisation.getDescription());

        // salle
        Salle salle = new Salle(1, "sal1", 5);
        salle.setCreationDate(new Date());
        salle.setCreatedBy("toto");

        com.example.training.dto.Salle salleDto = new com.example.training.dto.Salle();
        salleDto.setId(salle.getId());
        salleDto.setNom(salle.getNom());
        salleDto.setCapacite(salle.getCapacite());

        // employe
        Employe employe  = new Employe(1, "mat1001",
                "durand", "olivier"
                , new Date(), organisation);
        employe.setCreationDate(new Date());
        employe.setCreatedBy("toto");

        com.example.training.dto.Employe employeDto = new com.example.training.dto.Employe();
        employeDto.setId(employe.getId());
        employeDto.setMatricule(employe.getMatricule());
        employeDto.setNom(employe.getNom());
        employeDto.setPrenom(employe.getPrenom());
        employeDto.setDateEmbauche(employe.getDateEmbauche());
        employeDto.setOrganisation(organisationDto);

        // reservation
        Reservation reservation = new Reservation(1, new Date(), employe, salle);
        reservation.setCreationDate(new Date());
        reservation.setCreatedBy("toto");

        com.example.training.dto.Reservation reservationDto= new com.example.training.dto.Reservation();
        reservationDto.setId(reservation.getId());
        reservationDto.setDatetime(reservation.getDatetime());
        reservationDto.setEmploye(employeDto);
        reservationDto.setSalle(salleDto);

        when(reservationService.updateReservation(any()))
                .thenReturn(reservation);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/reservation")
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(reservationDto))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(reservation.getId())))
                .andReturn();
    }

    @Test
    public void deleteReservationTest() throws Exception{
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

        when(reservationService.deleteReservation(reservation.getId())).thenReturn(
                reservation);

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/api/reservation/{id}", reservation.getId())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                // assert reservation
                .andExpect(jsonPath("$.id", is(reservation.getId())))
                //.andExpect(jsonPath("$.datetime", is(reservation.getDatetime())))
                //  .andExpect(jsonPath("$.employe", is(reservation.getEmploye())))
                //  .andExpect(jsonPath("$.salle", is(reservation.getSalle())))
                .andReturn();
    }

}
