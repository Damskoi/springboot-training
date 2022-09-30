package com.example.training.repository.impl;

import com.example.training.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IReservationRepository extends JpaRepository<Reservation,Integer> {

}

