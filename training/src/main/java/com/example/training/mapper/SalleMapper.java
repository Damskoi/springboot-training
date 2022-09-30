package com.example.training.mapper;

import com.example.training.dto.Salle;

public class SalleMapper {

    public static Salle mapToDto(com.example.training.entity.Salle salle){
        Salle dto = new Salle();
        dto.setId(salle.getId());
        dto.setNom(salle.getNom());
        dto.setCapacite(salle.getCapacite());

        return dto;
    }

    public static com.example.training.entity.Salle mapToBo(Salle salle){
        com.example.training.entity.Salle bo = new com.example.training.entity.Salle();
        bo.setId(salle.getId());
        bo.setNom(salle.getNom());
        bo.setCapacite(salle.getCapacite());

        return bo;
    }
}
