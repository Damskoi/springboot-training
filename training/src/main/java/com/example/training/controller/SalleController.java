package com.example.training.controller;

import com.example.training.entity.Salle;
import com.example.training.mapper.SalleMapper;
import com.example.training.service.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/salle")
public class
SalleController {

    @Autowired
    SalleService salleService;

    //    @GetMapping("/api/salle")
    @GetMapping()
    public ResponseEntity<List<com.example.training.dto.Salle>> getAllSalles(){

        List<Salle> lesSalle = salleService.getAllSalles();

        List<com.example.training.dto.Salle> salles = lesSalle.parallelStream()
                .map(x-> SalleMapper.mapToDto(x))
                .collect(Collectors.toList());

        return new ResponseEntity<List<com.example.training.dto.Salle>>(salles, HttpStatus.OK);
    }

    //  @GetMapping("//api/salle/{id}")
    @GetMapping("/{id}")
    public  ResponseEntity<com.example.training.dto.Salle> getSalle(@PathVariable int id) {
        return new ResponseEntity<com.example.training.dto.Salle>(SalleMapper.mapToDto(salleService.getSalle(id)), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<com.example.training.dto.Salle> createSalle(@RequestBody com.example.training.dto.Salle salle){
        return new ResponseEntity<com.example.training.dto.Salle>(SalleMapper.mapToDto(salleService.createSalle(
                SalleMapper.mapToBo(salle))), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<com.example.training.dto.Salle> updateSalle(@RequestBody com.example.training.dto.Salle salle){
        return new ResponseEntity<com.example.training.dto.Salle>(SalleMapper.mapToDto(salleService.updateSalle(
                SalleMapper.mapToBo(salle))), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<com.example.training.dto.Salle> deleteSalle(@PathVariable int id){
        return new ResponseEntity<com.example.training.dto.Salle>(SalleMapper.mapToDto(salleService.deleteSalle(id)), HttpStatus.OK);
    }
}
