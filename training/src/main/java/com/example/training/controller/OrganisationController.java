package com.example.training.controller;

import com.example.training.entity.Organisation;
import com.example.training.mapper.OrganisationMapper;
import com.example.training.service.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/organisation")
public class OrganisationController {

    @Autowired
    OrganisationService organisationService;

    @GetMapping()
    public ResponseEntity<List<com.example.training.dto.Organisation>> getAllOrganisations(){

        List<Organisation> lesOrganisation = organisationService.getAllOrganisations();

        List<com.example.training.dto.Organisation> organisations = lesOrganisation.parallelStream()
                .map(x-> OrganisationMapper.mapToDto(x))
                .collect(Collectors.toList());

        return new ResponseEntity<List<com.example.training.dto.Organisation>>(organisations, HttpStatus.OK);
    }

    //  @GetMapping("//api/organisation/{id}")
    @GetMapping("/{id}")
    public  ResponseEntity<com.example.training.dto.Organisation> getOrganisation(@PathVariable int id) {
        return new ResponseEntity<com.example.training.dto.Organisation>(OrganisationMapper.mapToDto(organisationService.getOrganisation(id)), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<com.example.training.dto.Organisation> createOrganisation(@RequestBody com.example.training.dto.Organisation organisation){
        return new ResponseEntity<com.example.training.dto.Organisation>(OrganisationMapper.mapToDto(organisationService.createOrganisation(
                OrganisationMapper.mapToBo(organisation))), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<com.example.training.dto.Organisation> updateOrganisation(@RequestBody com.example.training.dto.Organisation organisation){
        return new ResponseEntity<com.example.training.dto.Organisation>(OrganisationMapper.mapToDto(organisationService.updateOrganisation(
                OrganisationMapper.mapToBo(organisation))), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<com.example.training.dto.Organisation> deleteOrganisation(@PathVariable int id){
        return new ResponseEntity<com.example.training.dto.Organisation>(OrganisationMapper.mapToDto(organisationService.deleteOrganisation(id)), HttpStatus.OK);
    }
}
