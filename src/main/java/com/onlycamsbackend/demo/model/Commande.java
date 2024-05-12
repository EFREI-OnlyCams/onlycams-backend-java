package com.onlycamsbackend.demo.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Commande")
public class Commande {
    @Id
    @ManyToOne
    @JoinColumn(name = "Utilisateur_Id")
    private Utilisateur utilisateur;

    @Id
    @ManyToOne
    @JoinColumn(name = "Product_Id")
    private Produit produit;

    @Column(name = "date_commande")
    private Date dateCommande;

    @Column(name = "statut")
    private String statut;

    @Column(name = "quantite")
    private int quantite;

    // Getters and setters
}

