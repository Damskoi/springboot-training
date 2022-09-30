package com.example.training.entity;

import com.example.training.thread.SuperBO;
import com.example.training.thread.UserContext;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "t_salle")
@Getter
@Setter
public class Salle extends SuperBO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "capacite", nullable = false)
    private int capacite;

    public Salle() {}

    public Salle(int id, String nom,
                 int capacite) {
        this.id = id;
        this.nom = nom;
        this.capacite = capacite;
    }
}
