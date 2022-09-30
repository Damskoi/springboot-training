package com.example.training.service;

import com.example.training.exception.TrainingException;
import com.example.training.entity.Employe;
import com.example.training.repository.impl.IEmployeRepository;
import com.example.training.repository.impl.IOrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class EmployeService {

    @Autowired
    private IEmployeRepository iEmployeRepository;

    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private IOrganisationRepository iOrganisationRepository;

    public List<Employe> getAllEmployes(){
        return iEmployeRepository.findAll();
    }

    public Employe getEmploye(int id){
        Optional<Employe> employe = checkIdExist(id);
        return employe.get();
    }

    public Employe createEmploye (Employe employe){
        checkEmploye(employe);
        return iEmployeRepository.save(employe);
    }

    public Employe updateEmploye (Employe employe){
        Optional<Employe> employeToUpdate = checkIdExist(employe.getId());
        checkEmploye(employe);

        employeToUpdate.get().setMatricule(employe.getMatricule());
        employeToUpdate.get().setNom(employe.getNom());
        employeToUpdate.get().setPrenom(employe.getPrenom());
        employeToUpdate.get().setDateEmbauche(employe.getDateEmbauche());
        employeToUpdate.get().setOrganisation(employe.getOrganisation());
        employeToUpdate.get().setUpdateDate(new Date());
        employeToUpdate.get().setUpdatedBy("toto");

        return iEmployeRepository.save(employeToUpdate.get());
    }

    public Employe deleteEmploye(int id){
        Optional<Employe> employeToDelete = checkIdExist(id);
        checkEmploye(employeToDelete.get());
        iEmployeRepository.delete(employeToDelete.get());
        return employeToDelete.get();
    }

    public void checkEmploye(Employe employe){
        if(employe == null)
            throw new TrainingException("L'objet n'est pas valide/null", HttpStatus.BAD_REQUEST);

        //nom
        if(employe.getNom() == null)
            throw new TrainingException("Le Nom n'est pas renseigné", HttpStatus.BAD_REQUEST);
        if(employe.getNom().strip().equals(""))
            throw new TrainingException("Le Nom est vide", HttpStatus.BAD_REQUEST);
        if (employe.getNom().length()>45)
            throw new TrainingException("Le Nom est trop long", HttpStatus.BAD_REQUEST);

        //prenom
        if(employe.getPrenom() == null)
            throw new TrainingException("Le Prenom n'est pas renseigné", HttpStatus.BAD_REQUEST);
        if(employe.getPrenom().strip().equals(""))
            throw new TrainingException("Le Prenom est vide", HttpStatus.BAD_REQUEST);
        if (employe.getPrenom().length()>45)
            throw new TrainingException("Le Prenom est trop long", HttpStatus.BAD_REQUEST);

        //date embauche
        if(employe.getDateEmbauche() == null)
            throw new TrainingException("La date non renseigné", HttpStatus.BAD_REQUEST);

        //matricule
        if(employe.getMatricule() == null)
            throw new TrainingException("La Matricule n'est pas renseigné", HttpStatus.BAD_REQUEST);
        if(employe.getMatricule().strip().equals(""))
            throw new TrainingException("La Matricule est vide", HttpStatus.BAD_REQUEST);
        if (employe.getMatricule().length()>45)
            throw new TrainingException("La Matricule est trop long", HttpStatus.BAD_REQUEST);

        // Organisation
        if(employe.getOrganisation() == null)
            throw new TrainingException("L'organisation n'est pas pas valide/null", HttpStatus.BAD_REQUEST);
        organisationService.checkIdExist(employe.getOrganisation().getId());
    }

    public Optional<Employe> checkIdExist(int id){
        Optional<Employe> employe = iEmployeRepository.findById(id);
        if(employe.isEmpty())
            throw new TrainingException("L'employé n'existe pas", HttpStatus.NOT_FOUND);
        else return employe;
    }
}
