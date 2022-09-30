package com.example.training.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Employe {

    private int id;

    private String matricule;

    private String nom;

    private String prenom;

    private Date dateEmbauche;

    private Organisation organisation;
}
