package com.example.training.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Reservation {

    private int id;

    private Date datetime;

    private Employe employe;

    private Salle salle;
}
