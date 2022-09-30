package com.example.training.repository.impl;

import com.example.training.entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IEmployeRepository extends JpaRepository<Employe,Integer> {

}