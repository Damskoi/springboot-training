package com.example.training.mapper;

import com.example.training.dto.Organisation;

import java.util.Date;

public class OrganisationMapper {

    public static Organisation mapToDto(com.example.training.entity.Organisation organisation){
        Organisation dto = new Organisation();
        dto.setId(organisation.getId());
        dto.setOrg(organisation.getOrg());
        dto.setNom(organisation.getNom());
        dto.setDescription(organisation.getDescription());

        return dto;
    }

    public static com.example.training.entity.Organisation mapToBo(Organisation organisation){
        com.example.training.entity.Organisation bo = new com.example.training.entity.Organisation();
        bo.setId(organisation.getId());
        bo.setOrg(organisation.getOrg());
        bo.setNom(organisation.getNom());
        bo.setDescription(organisation.getDescription());

        return bo;
    }
}
