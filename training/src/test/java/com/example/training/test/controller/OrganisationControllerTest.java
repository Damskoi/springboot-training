package com.example.training.test.controller;


import com.example.training.controller.OrganisationController;
import com.example.training.entity.Organisation;
import com.example.training.service.OrganisationService;
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
@WebMvcTest(controllers = OrganisationController.class)
public class OrganisationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrganisationService organisationService;
/*
    @MockBean
    private OrganisationMapper organisationMapper;
*/
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public  void  getAllOrganisationsTest()throws Exception{

        Date date = new Date();

        Organisation organisation0 = new Organisation(1, "abc", "org1", "une org" );
        organisation0.setCreationDate(date);
        organisation0.setCreatedBy("toto");

        Organisation organisation1 = new Organisation(2, "xyz", "org2", "une org" );
        organisation1.setCreationDate(date);
        organisation1.setCreatedBy("toto");

        ArrayList<Organisation> organisations = new ArrayList<Organisation>();
        organisations.add(organisation0);
        organisations.add(organisation1);

        when(organisationService.getAllOrganisations())
                .thenReturn(organisations);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/organisation")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())

                // assert nb organisation
                .andExpect(jsonPath("$", hasSize(2)))

                // assert organisation0
                .andExpect(jsonPath("$[0].id", is(organisation0.getId())))
                .andExpect(jsonPath("$[0].org", is(organisation0.getOrg())))
                .andExpect(jsonPath("$[0].nom", is(organisation0.getNom())))
                .andExpect(jsonPath("$[0].description", is(organisation0.getDescription())))

                // assert organisation1
                .andExpect(jsonPath("$[1].id", is(organisation1.getId())))
                .andExpect(jsonPath("$[1].org", is(organisation1.getOrg())))
                .andExpect(jsonPath("$[1].nom", is(organisation1.getNom())))
                .andExpect(jsonPath("$[1].description", is(organisation1.getDescription())))

                .andReturn();

    }

    @Test
    public  void  getOrganisationTest()throws Exception{

        Organisation organisation = new Organisation(1, "abc", "org1", "une org" );
        organisation.setCreationDate(new Date());
        organisation.setCreatedBy("toto");

        when(organisationService.getOrganisation(organisation.getId()))
                .thenReturn(organisation);


        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/organisation/{id}", organisation.getId())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                // assert Organisation
                .andExpect(jsonPath("$.id", is(organisation.getId())))
                .andExpect(jsonPath("$.org", is(organisation.getOrg())))
                .andExpect(jsonPath("$.nom", is(organisation.getNom())))
                .andExpect(jsonPath("$.description", is(organisation.getDescription())))
                .andReturn();
    }


    @Test
    public void CreateOrganisationTest() throws Exception{

        Organisation organisation = new Organisation(1, "abc", "org1", "une org" );
        organisation.setCreationDate(new Date());
        organisation.setCreatedBy("toto");

        com.example.training.dto.Organisation organisationDto = new com.example.training.dto.Organisation();
        organisationDto.setId(organisation.getId());
        organisationDto.setOrg(organisation.getOrg());
        organisationDto.setNom(organisation.getNom());
        organisationDto.setDescription(organisation.getDescription());

        when(organisationService.createOrganisation(any()))
                .thenReturn(organisation);


        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/organisation")
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(organisationDto))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(organisation.getId())))
                .andExpect(jsonPath("$.org", is(organisation.getOrg())))
                .andExpect(jsonPath("$.nom", is(organisation.getNom())))
                .andExpect(jsonPath("$.description", is(organisation.getDescription())))
                .andReturn();
    }

    @Test
    public void UpdateOrganisationTest() throws Exception{

        Organisation organisation = new Organisation(1, "abc", "org1", "ue org");
        organisation.setCreationDate(new Date());
        organisation.setCreatedBy("toto");

        com.example.training.dto.Organisation organisationDto = new com.example.training.dto.Organisation();
        organisationDto.setId(organisation.getId());
        organisationDto.setOrg(organisation.getOrg());
        organisationDto.setNom(organisation.getNom());
        organisationDto.setDescription(organisation.getDescription());

        when(organisationService.updateOrganisation(any())).thenReturn(organisation);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/organisation")
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(organisationDto))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(organisation.getId())))
                .andExpect(jsonPath("$.org", is(organisation.getOrg())))
                .andExpect(jsonPath("$.nom", is(organisation.getNom())))
                .andExpect(jsonPath("$.description", is(organisation.getDescription())))
                .andReturn();
    }

    @Test
    public  void  deleteOrganisationTest()throws Exception{

        Organisation organisation = new Organisation(1, "abc", "org1", "une org" );
        organisation.setCreationDate(new Date());
        organisation.setCreatedBy("toto");

        when(organisationService.deleteOrganisation(organisation.getId()))
                .thenReturn(organisation);

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/api/organisation/{id}", organisation.getId())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                // assert Organisation
                .andExpect(jsonPath("$.id", is(organisation.getId())))
                .andExpect(jsonPath("$.org", is(organisation.getOrg())))
                .andExpect(jsonPath("$.nom", is(organisation.getNom())))
                .andExpect(jsonPath("$.description", is(organisation.getDescription())))
                .andReturn();
    }
}
