package com.example.training.test.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.example.training.AbstractDbUnitTest;

import com.example.training.entity.Salle;
import com.example.training.exception.TrainingException;
import com.example.training.service.SalleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@DatabaseSetup(value = "classpath:datasets/Dataset.xml", type = DatabaseOperation.REFRESH)
@Transactional
public class SalleServiceTest extends AbstractDbUnitTest {

    @Autowired
    private SalleService salleService;

    @Test
    public void getAllSalles(){
        List<Salle> salles = salleService.getAllSalles();

        assertNotNull(salles);
        assertEquals(2, salles.size());
    }

    @Test
    public void getSalle(){
        Salle salle = salleService.getSalle(1);

        assertNotNull(salle);
        assertEquals("sal101", salle.getNom());
        assertEquals(5, salle.getCapacite());
    }

    @Test
    public void getSalleWithIdNotExisting_trowTrainingException(){

        assertThatThrownBy(() -> salleService.getSalle(5))
                .isInstanceOf(TrainingException.class)
                .hasMessageContaining("La salle n'existe pas");
    }

    @Test
    public void createSalleWithValidData(){

        Salle salle = new Salle(2, "sal102", 10);
        salle.setCreationDate(new Date());
        salle.setCreatedBy("toto");

        Salle salleCreated = salleService.createSalle(salle);

        assertEquals(salle.getNom(), salleCreated.getNom());
        assertEquals(salle.getCapacite(), salleCreated.getCapacite());
    }

    @Test()
    public void createSalleWithNullMandatoryField_trowTrainingException(){
        Salle salle= new Salle();

        assertThatThrownBy(() -> salleService.createSalle(salle))
                .isInstanceOf(TrainingException.class)
                .hasMessageContaining("Le nom n'est pas renseigné");
    }

    public void createSalleWithEmptyField_trowTrainingException(){
        Salle salle= new Salle(2, " ", 5);

        assertThatThrownBy(() -> salleService.createSalle(salle))
                .isInstanceOf(TrainingException.class)
                .hasMessageContaining("Le nom est vide");
    }

    @Test
    public void createSalleWithTooLongField_trowTrainingException(){
        Salle salle= new Salle(2,
                "chrishkjghjfjfjjghjghjhvfgffhfffffffffffffffffffffffffffffffffffffgggfgfgfghffh",
                5);

        assertThatThrownBy(() -> salleService.createSalle(salle))
                .isInstanceOf(TrainingException.class)
                .hasMessageContaining("Le nom est trop long");
    }

    @Test
    public void createSalleWithNegeativeCapacite_trowTrainingException(){
        Salle salle= new Salle(2, "salle1", 0);

        assertThatThrownBy(() -> salleService.createSalle(salle))
                .isInstanceOf(TrainingException.class)
                .hasMessageContaining("La capacité est est null");
    }

    @Test
    public void updateSalleTestWithValidData(){
        Salle salle= new Salle(1, "salle1", 5);
        salle.setUpdateDate(new Date());
        salle.setUpdatedBy("toto");

        Salle salleUpdated = salleService.updateSalle(salle);

        assertEquals(salle.getId(), salleUpdated.getId());
        assertEquals(salle.getNom(), salleUpdated.getNom());
        assertEquals(salle.getCapacite(), salleUpdated.getCapacite());
        assertEquals(salle.getUpdateDate(), salleUpdated.getUpdateDate());
        assertEquals(salle.getUpdatedBy(), salleUpdated.getUpdatedBy());
    }

    @Test
    public void updateSalleTestWithIDnoValid(){
        Salle salle= new Salle(5, "salle1", 5);
        salle.setUpdateDate(new Date());
        salle.setUpdatedBy("toto");

        assertThatThrownBy(() -> salleService.updateSalle(salle))
                .isInstanceOf(TrainingException.class)
                .hasMessageContaining("La salle n'existe pas");
    }

    @Test
    public void deleteSalleTestValide(){
        Salle salle= new Salle(2, "sal102", 5);

        Salle salledeleted = salleService.deleteSalle(salle.getId());

        assertEquals(salle.getNom(), salledeleted.getNom());
        assertEquals(salle.getCapacite(), salledeleted.getCapacite());


    }

    @Test
    public void deleteSalleTestWithIdNoValide() {

        Salle salle= new Salle(8, "sal101", 5);

        assertThatThrownBy(() -> salleService.deleteSalle(salle.getId()))
                .isInstanceOf(TrainingException.class)
                .hasMessageContaining("La salle n'existe pas");
    }















}
