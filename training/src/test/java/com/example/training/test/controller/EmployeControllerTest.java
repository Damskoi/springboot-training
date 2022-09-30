package com.example.training.test.controller;

//import org.junit.Test;
import com.example.training.controller.EmployeController;
import com.example.training.dto.Organisation;
import com.example.training.entity.Employe;
import com.example.training.service.EmployeService;
import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Test;
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

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EmployeController.class)
public class EmployeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeService employeService;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getAllEmployeTest() throws Exception{
        // organisation
        com.example.training.entity.Organisation organisation = new com.example.training.entity.Organisation(1, "cvb",
                "org", "un org");
        organisation.setCreationDate(new Date());
        organisation.setCreatedBy("toto");

        //employes to return
        Employe employe0 = new Employe(1, "mat10001", "chris", "durand", new Date(), organisation);
        employe0.setCreationDate(new Date());
        employe0.setCreatedBy("toto");

        Employe employe1 = new Employe(2, "mat10002", "hello", "world", new Date(), organisation);
        employe1.setCreationDate(new Date());
        employe1.setCreatedBy("toto");

        //List<Employe> test = Arrays.asList(employe0, employe1);


        when(employeService.getAllEmployes()).thenReturn(
                Arrays.asList(employe0, employe1));

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/employe")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())

                // assert nb employe
                .andExpect(jsonPath("$", hasSize(2)))

                // assert employe0
                .andExpect(jsonPath("$[0].id", is(employe0.getId())))
                .andExpect(jsonPath("$[0].matricule", is(employe0.getMatricule())))
                .andExpect(jsonPath("$[0].nom", is(employe0.getNom())))
                .andExpect(jsonPath("$[0].prenom", is(employe0.getPrenom())))
              //  .andExpect(jsonPath("$[0].dateEmbauche", is(employe0.getDateEmbauche())))

                // assert employe1
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].id", is(employe1.getId())))
                .andExpect(jsonPath("$[1].matricule", is(employe1.getMatricule())))
                .andExpect(jsonPath("$[1].nom", is(employe1.getNom())))
                .andExpect(jsonPath("$[1].prenom", is(employe1.getPrenom())))
                //.andExpect(jsonPath("$[0].id", is(employe1.getId())))

                .andReturn();

       // verify(employeService).getAllEmployes();
 //       verifyNoMoreInteractions(employeService);
    }

    @Test
    public void getEmployeTest() throws Exception{
        // organisation
        com.example.training.entity.Organisation organisation = new com.example.training.entity.Organisation(1, "cvb",
                "org", "un org");
        organisation.setCreationDate(new Date());
        organisation.setCreatedBy("toto");

        //employes to return
        Employe employe = new Employe(1, "mat10001", "chris", "durand", new Date(), organisation);
        employe.setCreationDate(new Date());
        employe.setCreatedBy("toto");

        when(employeService.getEmploye(employe.getId())).thenReturn(
                employe);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/employe/"+ String.valueOf(employe.getId()))
               // .param("id", String.valueOf(employe.getId()))
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())

                // assert employe
                .andExpect(jsonPath("$.id", is(employe.getId())))
                .andExpect(jsonPath("$.matricule", is(employe.getMatricule())))
                .andExpect(jsonPath("$.nom", is(employe.getNom())))
                .andExpect(jsonPath("$.prenom", is(employe.getPrenom())))
                .andReturn();
    }

    @Test
    public void createEmployeTest() throws Exception{

        Date date = new Date();
        // organisation bo
        com.example.training.entity.Organisation organisation = new com.example.training.entity.Organisation(1, "abc",
                "organ1", "une organisation");
        organisation.setCreationDate(new Date());
        organisation.setCreatedBy("toto");

        // organisation dto
        Organisation organisationDto = new Organisation();
        organisation.setId(organisation.getId());
        organisation.setNom(organisation.getOrg());
        organisation.setNom(organisation.getNom());

        // employe bo
        Employe employe = new Employe(1, "mat10001",
                "durand", "olivier", date, organisation);
        employe.setCreationDate(new Date());
        employe.setCreatedBy("toto");

        // employe dto
        com.example.training.dto.Employe employeDto = new com.example.training.dto.Employe();
        employeDto.setId(employe.getId());
        employeDto.setMatricule(employe.getMatricule());
        employeDto.setNom(employe.getNom());
        employeDto.setPrenom(employe.getPrenom());
        employeDto.setDateEmbauche(employe.getDateEmbauche());
        employeDto.setOrganisation(organisationDto);

        when(employeService.createEmploye(any())).thenReturn(employe);
   //     given(employeService.createEmploye(employe)).willReturn(employe);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/employe")
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(employeDto))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())

                // assert employe
                .andExpect(jsonPath("$.id", is(employe.getId())))
                .andExpect(jsonPath("$.matricule", is(employe.getMatricule())))
                .andExpect(jsonPath("$.nom", is(employe.getNom())))
                .andExpect(jsonPath("$.prenom", is(employe.getPrenom())))
                .andReturn();
    }

    @Test
    public void updateEmployeTest() throws Exception{

        Date date = new Date();
        // organisation bo
        com.example.training.entity.Organisation organisation = new com.example.training.entity.Organisation(1, "abc",
                "organ1", "une organisation");
        organisation.setCreationDate(new Date());
        organisation.setCreatedBy("toto");

        // organisation dto
        Organisation organisationDto = new Organisation();
        organisation.setId(organisation.getId());
        organisation.setNom(organisation.getOrg());
        organisation.setNom(organisation.getNom());

        // employe bo
        Employe employe = new Employe(1, "mat10001",
                "durand", "olivier", date, organisation);
        employe.setCreationDate(new Date());
        employe.setCreatedBy("toto");

        // employe dto
        com.example.training.dto.Employe employeDto = new com.example.training.dto.Employe();
        employeDto.setId(employe.getId());
        employeDto.setMatricule(employe.getMatricule());
        employeDto.setNom(employe.getNom());
        employeDto.setPrenom(employe.getPrenom());
        employeDto.setDateEmbauche(employe.getDateEmbauche());
        employeDto.setOrganisation(organisationDto);

        when(employeService.updateEmploye(any())).thenReturn(employe);
        //     given(employeService.createEmploye(employe)).willReturn(employe);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/employe")
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(employeDto))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())

                // assert employe
                .andExpect(jsonPath("$.id", is(employe.getId())))
                .andExpect(jsonPath("$.matricule", is(employe.getMatricule())))
                .andExpect(jsonPath("$.nom", is(employe.getNom())))
                .andExpect(jsonPath("$.prenom", is(employe.getPrenom())))
                .andReturn();
    }

    @Test
    public void deleteEmployeTest() throws Exception{

        // organisation
        com.example.training.entity.Organisation organisation = new com.example.training.entity.Organisation(1, "cvb",
                "org", "un org");
        organisation.setCreationDate(new Date());
        organisation.setCreatedBy("toto");

        //employes to return
        Employe employe = new Employe(1, "mat10001", "chris", "durand", new Date(), organisation);
        employe.setCreationDate(new Date());
        employe.setCreatedBy("toto");

        when(employeService.deleteEmploye(employe.getId())).thenReturn(
                employe);

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/api/employe/{id}", (employe.getId()))
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())

                // assert employe
                .andExpect(jsonPath("$.id", is(employe.getId())))
                .andExpect(jsonPath("$.matricule", is(employe.getMatricule())))
                .andExpect(jsonPath("$.nom", is(employe.getNom())))
                .andExpect(jsonPath("$.prenom", is(employe.getPrenom())))
              //  .andExpect(js)
                .andReturn();
    }

    
}
