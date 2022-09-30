package com.example.training.test.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.example.training.AbstractDbUnitTest;

import com.example.training.entity.Organisation;
import com.example.training.exception.TrainingException;
import com.example.training.service.OrganisationService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@DatabaseSetup(value = "classpath:datasets/Dataset.xml", type = DatabaseOperation.REFRESH)
@Transactional
public class OrganisationServiceTest extends AbstractDbUnitTest {

    @Autowired
    private OrganisationService organisationService;

    @Test
    public void getAllOrganisationsTest() {

        List<Organisation> organisations = organisationService.getAllOrganisations();

        assertNotNull(organisations);
        assertEquals(2, organisations.size());
    }

    @Test
    public void getOrganisation(){

        Organisation organisation = organisationService.getOrganisation(1);

        assertNotNull(organisation);

    }


    @Test
    public void createOrganisationWithValidData(){
        Organisation organisation = new Organisation(1, "abc",
                "org1", "first org");
        organisation.setCreationDate(new Date());
        organisation.setCreatedBy("toto");

        Organisation organisationSaved = organisationService.createOrganisation(organisation);
        assertEquals(organisation.getNom(), organisationSaved.getNom());
    }

    @Test
    public void createOrganisationWithNullMandatoryField_trowTrainingException() {
        Organisation organisation = new Organisation();

        assertThatThrownBy(() -> organisationService.createOrganisation(organisation))
                .isInstanceOf(TrainingException.class)
                .hasMessageContaining("L'organisation ID n'est pas renseigné");
    }

    @Test
    public void createOrganisationWithEmptyField_trowTrainingException() {
        Organisation organisation = new Organisation(1, "abc",
                "", "first org");
        organisation.setCreationDate(new Date());
        organisation.setCreatedBy("toto");

        assertThatThrownBy(() -> organisationService.createOrganisation(organisation))
                .isInstanceOf(TrainingException.class)
                .hasMessageContaining("Le Nom est vide");
    }

    @Test
    public void createOrganisationWithTooLongField_trowTrainingException() {
        Organisation organisation = new Organisation(1, "abchghgjgh",
                "org1", "first org");
        organisation.setCreationDate(new Date());
        organisation.setCreatedBy("toto");

        assertThatThrownBy(() -> organisationService.createOrganisation(organisation))
                .isInstanceOf(TrainingException.class)
                .hasMessageContaining("L'organisation ID est trop long");
    }


    @Test
    public void updateOrganisationWithValidData(){
        Organisation organisation = new Organisation(1, "ab",
                "org1", "first org");
        organisation.setCreationDate(new Date());
        organisation.setCreatedBy("toto");

        Organisation organisationUpdated = organisationService.updateOrganisation(organisation);
        assertEquals(organisation.getOrg(), organisationUpdated.getOrg());
        assertEquals(organisation.getNom(), organisationUpdated.getNom());
        assertEquals(organisation.getDescription(), organisationUpdated.getDescription());
    }


    @Test
    public void updateOrganisationWithNoValidId_trowTrainingException() {
        Organisation organisation = new Organisation(4, "ab",
                "org1", "first org");
        organisation.setCreationDate(new Date());
        organisation.setCreatedBy("toto");

        assertThatThrownBy(() -> organisationService.updateOrganisation(organisation))
                .isInstanceOf(TrainingException.class)
                .hasMessageContaining("L'organisation n'existe pas");
    }

    @Test
    public void deleteOrganisation(){
        Organisation organisation = new Organisation(2, "aze",
                "org2", "second org");
        organisation.setCreationDate(new Date());
        organisation.setCreatedBy("toto");

        Organisation organisationdeleted = organisationService.deleteOrganisation(organisation.getId());

        assertEquals(organisation.getNom(), organisationdeleted.getNom());
        assertEquals(organisation.getDescription(), organisationdeleted.getDescription());

    }

    @Test
    public void deleteOrganisationTestWithIdNoValide_trowTrainingException(){
        Organisation organisation = new Organisation(10, "abc",
                "org1", "first org");
        organisation.setCreationDate(new Date());
        organisation.setCreatedBy("toto");

        assertThatThrownBy(() -> organisationService.deleteOrganisation(organisation.getId()))
                .isInstanceOf(TrainingException.class)
                .hasMessageContaining("L'organisation n'existe pas");

    }

}
