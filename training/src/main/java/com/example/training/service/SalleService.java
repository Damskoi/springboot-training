package com.example.training.service;

import com.example.training.exception.TrainingException;
import com.example.training.entity.Salle;
import com.example.training.repository.impl.ISalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SalleService {

    @Autowired
    ISalleRepository iSalleRepository;

    public List<Salle> getAllSalles(){
        return iSalleRepository.findAll();
    }

    public Salle getSalle(int id){
        return checkIDExist(id);
    }

    public Salle createSalle(Salle salle){
        checkSalle(salle);
        return  iSalleRepository.save(salle);
    }

    public Salle updateSalle(Salle salle){
        Salle salleToUpdate = checkIDExist(salle.getId());
        checkSalle(salle);
        salleToUpdate.setNom(salle.getNom());
        salleToUpdate.setCapacite(salle.getCapacite());
        salleToUpdate.setUpdateDate(salle.getUpdateDate());
        salleToUpdate.setUpdatedBy(salle.getUpdatedBy());
        return iSalleRepository.save((salleToUpdate));
    }

    public Salle deleteSalle(int id){
        Salle salle = checkIDExist(id);
        iSalleRepository.delete(salle);
        return salle;
    }


    public void checkSalle(Salle salle){
        if (salle == null)
            throw new TrainingException("L'objet n'est pas valide/null", HttpStatus.BAD_REQUEST);

        //nom
        if (salle.getNom() == null)
            throw new TrainingException("Le nom n'est pas renseigné", HttpStatus.BAD_REQUEST);
        if (salle.getNom().strip().equals(""))
            throw new TrainingException("Le nom est vide", HttpStatus.BAD_REQUEST);
        if (salle.getNom().length() > 45)
            throw new TrainingException("Le nom est trop long", HttpStatus.BAD_REQUEST);

        //capacité
        if (salle.getCapacite() <=0)
            throw new TrainingException("La capacité est est null", HttpStatus.BAD_REQUEST);

    }

    public Salle checkIDExist(int id){
        Optional<Salle> salle = iSalleRepository.findById(id);
        if(salle.isEmpty())
            throw new TrainingException("La salle n'existe pas", HttpStatus.NOT_FOUND);
        else return salle.get();
    }
}
