package com.example.training.repository.impl;

import com.example.training.entity.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IOrganisationRepository extends JpaRepository<Organisation,Integer> {


}


