package com.example.training.service;

import com.example.training.exception.TrainingException;
import com.example.training.entity.Organisation;
import com.example.training.repository.impl.IOrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrganisationService {

    @Autowired
    IOrganisationRepository iOrganisationRepository;

    public List<Organisation> getAllOrganisations() {
        return iOrganisationRepository.findAll();
    }

    public Organisation getOrganisation(int id) {
        Optional<Organisation> organisation = checkIdExist(id);
        return organisation.get();
    }

    public Organisation createOrganisation(Organisation organisation) {
        checkOrganisation(organisation);
        return iOrganisationRepository.save(organisation);
    }

    public Organisation updateOrganisation(Organisation organisation){
        Optional<Organisation> organisationToUpdate = checkIdExist(organisation.getId());

        organisationToUpdate.get().setOrg(organisation.getOrg());
        organisationToUpdate.get().setNom(organisation.getNom());
        organisationToUpdate.get().setDescription(organisation.getDescription());

        checkOrganisation(organisationToUpdate.get());
        return iOrganisationRepository.save(organisationToUpdate.get());
    }

    public Organisation deleteOrganisation(int id){
        Optional<Organisation> organisationToDelete = checkIdExist(id);
        checkOrganisation(organisationToDelete.get());
        iOrganisationRepository.delete(organisationToDelete.get());
        return organisationToDelete.get();
    }

    public void checkOrganisation(Organisation organisation) {
        if (organisation == null)
            throw new TrainingException("L'objet n'est pas valide/null", HttpStatus.BAD_REQUEST);

        //org
        if (organisation.getOrg() == null)
            throw new TrainingException("L'organisation ID n'est pas renseigné", HttpStatus.BAD_REQUEST);
        if (organisation.getOrg().strip().equals(""))
            throw new TrainingException("L'organisation ID est vide", HttpStatus.BAD_REQUEST);
        if (organisation.getOrg().length() > 3)
            throw new TrainingException("L'organisation ID est trop long", HttpStatus.BAD_REQUEST);

        //nom
        if (organisation.getNom() == null)
            throw new TrainingException("Le Nom n'est pas renseigné", HttpStatus.BAD_REQUEST);
        if (organisation.getNom().strip().equals(""))
            throw new TrainingException("Le Nom est vide", HttpStatus.BAD_REQUEST);
        if (organisation.getNom().length() > 45)
            throw new TrainingException("Le Nom est trop long", HttpStatus.BAD_REQUEST);

        //description
        if (organisation.getDescription() == null)
            throw new TrainingException("La Description n'est pas renseigné", HttpStatus.BAD_REQUEST);
        if (organisation.getDescription().strip().equals(""))
            throw new TrainingException("La Description est vide", HttpStatus.BAD_REQUEST);
        if (organisation.getDescription().length() > 100)
            throw new TrainingException("La Description est trop long", HttpStatus.BAD_REQUEST);
    }

    public Optional<Organisation> checkIdExist(int id){
        Optional<Organisation> organisation = iOrganisationRepository.findById(id);
        if(organisation.isEmpty())
            throw new TrainingException("L'organisation n'existe pas", HttpStatus.NOT_FOUND);
        else return organisation;
    }
}
