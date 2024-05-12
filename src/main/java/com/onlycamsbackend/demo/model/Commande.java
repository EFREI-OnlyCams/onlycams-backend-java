package com.onlycamsbackend.demo.model;

import com.onlycamsbackend.demo.model.CommandeId;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Commande")
public class Commande {

    @EmbeddedId
    private CommandeId id;

    @Column(name = "date_commande")
    private Date dateCommande;

    @Column(name = "statut")
    private String statut;

    @Column(name = "quantite")
    private int quantite;

    // Getters and setters
}
