package com.example.training.test.controller;


import com.example.training.controller.SalleController;
import com.example.training.entity.Salle;
import com.example.training.service.SalleService;
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
@WebMvcTest(controllers = SalleController.class)
public class SalleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalleService salleService;


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getAllSallesTest() throws Exception{
        Date date = new Date();
        Salle salle0 = new Salle(1, "sal101", 5);
        salle0.setCreationDate(date);
        salle0.setCreatedBy("toto");

        Salle salle1 = new Salle(2, "sal102", 10);
        salle1.setCreationDate(date);
        salle1.setCreatedBy("toto");

        ArrayList<Salle> salles = new ArrayList<Salle>();
        salles.add(salle0);
        salles.add(salle1);

        when(salleService.getAllSalles())
                .thenReturn(salles);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/salle")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())

                // assert nb salle
                .andExpect(jsonPath("$", hasSize(2)))

                // assert Salle0
                .andExpect(jsonPath("$[0].id", is(salle0.getId())))
                .andExpect(jsonPath("$[0].nom", is(salle0.getNom())))
                .andExpect(jsonPath("$[0].capacite", is(salle0.getCapacite())))

                // assert Salle1
                .andExpect(jsonPath("$[1].id", is(salle1.getId())))
                .andExpect(jsonPath("$[1].nom", is(salle1.getNom())))
                .andExpect(jsonPath("$[1].capacite", is(salle1.getCapacite())))

                .andReturn();
    }

    @Test
    public void getSalleTest() throws Exception{

        Salle salle = new Salle(1, "sal101", 5);
        salle.setCreationDate(new Date());
        salle.setCreatedBy("toto");

        when(salleService.getSalle(salle.getId()))
                .thenReturn(salle);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/salle/{id}", salle.getId())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                // assert Salle
                .andExpect(jsonPath("$.id", is(salle.getId())))
                .andExpect(jsonPath("$.nom", is(salle.getNom())))
                .andExpect(jsonPath("$.capacite", is(salle.getCapacite())))
                .andReturn();
    }

    @Test
    public void createSalleTest() throws Exception{
        Salle salle = new Salle(1, "sal101", 5);
        salle.setCreationDate(new Date());
        salle.setCreatedBy("toto");

        com.example.training.dto.Salle salleDto = new com.example.training.dto.Salle();
        salleDto.setId(salle.getId());
        salleDto.setNom(salle.getNom());
        salleDto.setCapacite(salle.getCapacite());

        when(salleService.createSalle(any())).thenReturn(
                salle);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/salle")
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(salleDto))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())

                // assert salle
                .andExpect(jsonPath("$.id", is(salle.getId())))
                .andExpect(jsonPath("$.nom", is(salle.getNom())))
                .andExpect(jsonPath("$.capacite", is(salle.getCapacite())))
                .andReturn();
    }

    @Test
    public void updateSalleTest() throws Exception{
        Salle salle = new Salle(1, "sal101", 5);
        salle.setCreationDate(new Date());
        salle.setCreatedBy("toto");

        com.example.training.dto.Salle salleDto = new com.example.training.dto.Salle();
        salleDto.setId(salle.getId());
        salleDto.setNom(salle.getNom());
        salleDto.setCapacite(salle.getCapacite());

        when(salleService.updateSalle(any())).thenReturn(
                salle);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/salle")
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(salleDto))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())

                // assert salle
                .andExpect(jsonPath("$.id", is(salle.getId())))
                .andExpect(jsonPath("$.nom", is(salle.getNom())))
                .andExpect(jsonPath("$.capacite", is(salle.getCapacite())))
                .andReturn();
    }

    @Test
    public void deleteSalleTest() throws Exception{

        Salle salle = new Salle(1, "sal101", 5);
        salle.setCreationDate(new Date());
        salle.setCreatedBy("toto");

        when(salleService.deleteSalle(salle.getId())).thenReturn(
                salle);

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/api/salle/{id}", salle.getId())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())

                // assert salle
                .andExpect(jsonPath("$.id", is(salle.getId())))
                .andExpect(jsonPath("$.nom", is(salle.getNom())))
                .andExpect(jsonPath("$.capacite", is(salle.getCapacite())))
                .andReturn();
    }
}
