package com.example.training.controller;

import com.example.training.entity.Employe;
import com.example.training.mapper.EmployeMapper;
import com.example.training.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employe")
public class EmployeController {

    @Autowired
    EmployeService employeService;

//    @GetMapping("/api/employe")
    @GetMapping()
    public ResponseEntity<List<com.example.training.dto.Employe>> getAllEmployes(){

        List<Employe> lesEmploye = employeService.getAllEmployes();

        List<com.example.training.dto.Employe> employes = lesEmploye.parallelStream()
                .map(x-> EmployeMapper.mapToDto(x))
                .collect(Collectors.toList());

        return new ResponseEntity<List<com.example.training.dto.Employe>>(employes, HttpStatus.OK);
    }

  //  @GetMapping("//api/employe/{id}")
    @GetMapping("/{id}")
    public  ResponseEntity<com.example.training.dto.Employe> getEmploye(@PathVariable int id) {
        return new ResponseEntity<com.example.training.dto.Employe>(EmployeMapper.mapToDto(employeService.getEmploye(id)), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<com.example.training.dto.Employe> createEmploye(@RequestBody com.example.training.dto.Employe employe){
        return new ResponseEntity<com.example.training.dto.Employe>(EmployeMapper.mapToDto(employeService.createEmploye(
                EmployeMapper.mapToBo(employe))), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<com.example.training.dto.Employe> updateEmploye(@RequestBody com.example.training.dto.Employe employe){
        return new ResponseEntity<com.example.training.dto.Employe>(EmployeMapper.mapToDto(employeService.updateEmploye(
                EmployeMapper.mapToBo(employe))), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<com.example.training.dto.Employe> deleteEmploye(@PathVariable int id){
        return new ResponseEntity<com.example.training.dto.Employe>(EmployeMapper.mapToDto(employeService.deleteEmploye(id)), HttpStatus.OK);
    }
}
