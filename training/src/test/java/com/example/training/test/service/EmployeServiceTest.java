package com.example.training.test.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.example.training.AbstractDbUnitTest;

import com.example.training.entity.Employe;
import com.example.training.entity.Organisation;
import com.example.training.exception.TrainingException;
import com.example.training.service.EmployeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@DatabaseSetup(value = "classpath:datasets/Dataset.xml", type = DatabaseOperation.REFRESH)
@Transactional
public class EmployeServiceTest extends AbstractDbUnitTest  {

    @Autowired
    private EmployeService employeService;


    public Date formatStringToInstant(String time){
        Instant dateTime = Instant.parse(time);
        return Date.from(dateTime);
    }

    public Date formatTime(String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);

        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

        return date;
       // return dateTime;

    }

    @Test
    public void sample(){
        System.out.println("time");
      //  System.out.println(new LocalDate());
      //  System.out.println(java.sql.Date.valueOf("2022-01-01 10:54:30.067"));
        System.out.println(formatTime("2022-01-01 10:54:30.067"));

    }


    @Test
    public void getAllEmployesTest() {

        List<Employe> employes = employeService.getAllEmployes();

        assertNotNull(employes);
        assertEquals(2, employes.size());
    }


    @Test
    public void createEmployeWithValidData(){
        Organisation organisation = new Organisation();
        organisation.setId(1);
        Employe employe = new Employe(1, "mat10001", "chris", "durand", new Date(), organisation);
        employe.setCreationDate(new Date());
        employe.setCreatedBy("toto");

        Employe employeSaved = employeService.createEmploye(employe);

        assertEquals(employe.getMatricule(), employeSaved.getMatricule());
        assertEquals(employe.getNom(), employeSaved.getNom());
        assertEquals(employe.getPrenom(), employeSaved.getPrenom());
        assertEquals(employe.getDateEmbauche(), employeSaved.getDateEmbauche());
        assertEquals(employe.getOrganisation().getId(), employeSaved.getOrganisation().getId());
        assertEquals(employe.getCreationDate(), employeSaved.getCreationDate());
        assertEquals(employe.getCreatedBy(), employeSaved.getCreatedBy());
        assertEquals(employe.getNom(), employeSaved.getNom());

    }

    @Test
    public void createEmployeWithNoExistingOrganisation(){
        Organisation organisation = new Organisation();
        organisation.setId(8);
        Employe employe = new Employe(1, "mat10001", "chris", "durand", new Date(), organisation);
        employe.setCreationDate(new Date());
        employe.setCreatedBy("toto");

        assertThatThrownBy(() -> employeService.createEmploye(employe))
                .isInstanceOf(TrainingException.class)
                .hasMessageContaining("L'organisation n'existe pas");
    }

    @Test()
    public void createEmployeWithNullMandatoryField_trowTrainingException(){
        Employe employe = new Employe();

        assertThatThrownBy(() -> employeService.createEmploye(employe))
                .isInstanceOf(TrainingException.class)
                .hasMessageContaining("Le Nom n'est pas renseigné");
    }


    @Test()
    public void createEmployeWithEmptyField_trowTrainingException(){

        Organisation organisation = new Organisation();
        organisation.setId(1);
        Employe employe = new Employe(1, "mat10001"
                , " ",
                "durand", new Date(), organisation);
        employe.setOrganisation(organisation);
        employe.setCreationDate(new Date());
        employe.setCreatedBy("toto");

        assertThatThrownBy(() -> employeService.createEmploye(employe))
                .isInstanceOf(TrainingException.class)
                .hasMessageContaining("Le Nom est vide");
    }

    @Test()
    public void createEmployeWithTooLongField_trowTrainingException(){

        Organisation organisation = new Organisation();
        organisation.setId(1);
        Employe employe = new Employe(1, "mat10001",
                "chrishkjghjfjfjjghjghjhvfgffhfffffffffffffffffffffffffffffffffffffgggfgfgfghffh",
                "durand", new Date(), organisation);
        employe.setOrganisation(organisation);
        employe.setCreationDate(new Date());
        employe.setCreatedBy("toto");

        try {
            Employe employeSaved = employeService.createEmploye(employe);
        }catch (TrainingException trainingException){
              assert(trainingException.getMessage().contains("Le Nom est trop long"));
            System.out.println("message: "+ trainingException.getMessage() );
        }
    }

    @Test
    public void updateEmployeTestValide(){
        Organisation organisation = new Organisation();
        organisation.setId(1);
        Employe employe = new Employe(1, "mat10001", "hello", "world", new Date(), organisation);
        employe.setCreationDate(formatTime("2022-01-01 10:54:30.067"));
        employe.setCreatedBy("toto");

        Employe employeUpdated = employeService.updateEmploye(employe);
        assertEquals(employe.getMatricule(), employeUpdated.getMatricule());
        assertEquals(employe.getNom(), employeUpdated.getNom());
        assertEquals(employe.getPrenom(), employeUpdated.getPrenom());
        assertEquals(employe.getDateEmbauche(), employeUpdated.getDateEmbauche());
        assertEquals(employe.getOrganisation().getId(), employeUpdated.getOrganisation().getId());
    //    assertEquals(employe.getCreationDate(), employeUpdated.getCreationDate()); // a voir!!!!!!!!!!!!!!!!!!!!!
        assertEquals(employe.getCreatedBy(), employeUpdated.getCreatedBy());
        assertEquals(employe.getNom(), employeUpdated.getNom());

        System.out.println("test"+ employe.getCreationDate().getClass().getSimpleName());
        System.out.println("test"+ employeUpdated.getCreationDate().getClass().getSimpleName());

    }

    @Test
    public void updateEmployeTestWithIDnoValid(){
        Organisation organisation = new Organisation();
        organisation.setId(1);
        Employe employe = new Employe(4, "mat10001", "hello", "world", new Date(), organisation);
        employe.setCreationDate(new Date());
        employe.setCreatedBy("toto");

        assertThatThrownBy(() -> employeService.updateEmploye(employe))
                .isInstanceOf(TrainingException.class)
                .hasMessageContaining("L'employé n'existe pas");
    }

    @Test
    public void deleteEmployeTestValide() {
        Organisation organisation = new Organisation();
        organisation.setId(1);
        Employe employe = new Employe(2, "mat10002",
                "mornal", "christophe",
                formatTime("2022-01-01 10:54:30.067"), organisation);
        employe.setCreationDate(formatTime("2022-01-01 10:54:30.067"));
        employe.setCreatedBy("toto");
        employe.setUpdateDate(formatTime("2022-01-02 10:54:30.067"));
        employe.setUpdatedBy("toto");

        Employe employeDeleted = employeService.deleteEmploye(employe.getId());
        assertEquals(employe.getMatricule(), employeDeleted.getMatricule());
        assertEquals(employe.getNom(), employeDeleted.getNom());
        assertEquals(employe.getPrenom(), employeDeleted.getPrenom());
      //  assertEquals(employe.getDateEmbauche(), employeDeleted.getDateEmbauche()); // a voir !!!!!!
        assertEquals(employe.getOrganisation().getId(), employeDeleted.getOrganisation().getId());
      //  assertEquals(employe.getCreationDate(), employeDeleted.getCreationDate());
        assertEquals(employe.getCreatedBy(), employeDeleted.getCreatedBy());
    //    assertEquals(employe.getUpdateDate(), employeDeleted.getUpdateDate());
        assertEquals(employe.getUpdatedBy(), employeDeleted.getUpdatedBy());

        System.out.println();
    }

    @Test
    public void deleteEmployeTestWithIdNoValide() {
        Organisation organisation = new Organisation();
        organisation.setId(1);
        Employe employe = new Employe(4, "mat10001", "hello", "world", new Date(), organisation);
        employe.setCreationDate(new Date());
        employe.setCreatedBy("toto");

        assertThatThrownBy(() -> employeService.deleteEmploye(employe.getId()))
                .isInstanceOf(TrainingException.class)
                .hasMessageContaining("L'employé n'existe pas");
    }
}
