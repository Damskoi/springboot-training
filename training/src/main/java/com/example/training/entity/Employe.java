package com.example.training.entity;

import com.example.training.thread.SuperBO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "t_employe")
@Getter
@Setter
public class Employe extends SuperBO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "matricule", nullable = false)
    private String matricule;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "date_embauche", nullable = false)
    private Date dateEmbauche;

    @OneToOne
    @JoinColumn(name = "fk_organisation_id", referencedColumnName = "id")
    private Organisation organisation;

    public Employe() {
    }

    public Employe(int id,
                   String matricule,
                   String nom, String prenom,
                   Date dateEmbauche,
                   Organisation organisation) {
        this.id = id;
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.dateEmbauche = dateEmbauche;
        this.organisation = organisation;
    }
}
