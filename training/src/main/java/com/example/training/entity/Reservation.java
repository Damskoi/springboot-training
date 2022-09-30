package com.example.training.entity;

import com.example.training.thread.SuperBO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "t_reservation")
@Getter
@Setter
public class Reservation extends SuperBO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "date_time", nullable = false)
    private Date datetime;

    @OneToOne
    @JoinColumn(name = "fk_employe_id", referencedColumnName = "id")
    private Employe employe;

    @OneToOne
    @JoinColumn(name = "fk_salle_id", referencedColumnName = "id")
    private Salle salle;

    public Reservation() {}

    public Reservation(int id, Date datetime, Employe employe, Salle salle) {
        this.id = id;
        this.datetime = datetime;
        this.employe = employe;
        this.salle = salle;
    }
}
