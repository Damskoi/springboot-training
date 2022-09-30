package com.example.training.mapper;

import com.example.training.dto.Employe;

public class EmployeMapper {

    public static Employe mapToDto(com.example.training.entity.Employe employe){
        Employe dto = new Employe();
        dto.setId(employe.getId());
        dto.setMatricule(employe.getMatricule());
        dto.setNom(employe.getNom());
        dto.setPrenom(employe.getPrenom());
        dto.setOrganisation(OrganisationMapper.mapToDto(employe.getOrganisation()));
        dto.setDateEmbauche(employe.getDateEmbauche());

        return dto;
    }

    public static com.example.training.entity.Employe mapToBo(Employe employe){
        com.example.training.entity.Employe bo = new com.example.training.entity.Employe();
        bo.setId(employe.getId());
        bo.setMatricule(employe.getMatricule());
        bo.setNom(employe.getNom());
        bo.setPrenom(employe.getPrenom());
        bo.setOrganisation(OrganisationMapper.mapToBo(employe.getOrganisation()));
        bo.setDateEmbauche(employe.getDateEmbauche());

        return bo;
    }
}
