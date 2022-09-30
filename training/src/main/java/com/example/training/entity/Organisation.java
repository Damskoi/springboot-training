package com.example.training.entity;

import com.example.training.thread.SuperBO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_organisation")
@Getter
@Setter
public class Organisation extends SuperBO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "org", nullable = false)
    private String org;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "description", nullable = false)
    private String description;

    public Organisation() {}

    public Organisation(int id,
                        String org,
                        String nom,
                        String description) {
        this.id = id;
        this.org = org;
        this.nom = nom;
        this.description = description;
    }

}
