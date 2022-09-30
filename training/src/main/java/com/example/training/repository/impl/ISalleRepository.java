package com.example.training.repository.impl;

import com.example.training.entity.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ISalleRepository extends JpaRepository<Salle,Integer> {

}

