package com.onlycamsbackend.demo;

import jakarta.persistence.*;
@Entity
@Table(name = "Panier")
public class Panier {
    @Id
    @ManyToOne
    @JoinColumn(name = "Utilisateur_Id")
    private Utilisateur utilisateur;

    @Id
    @ManyToOne
    @JoinColumn(name = "Product_Id")
    private Produit produit;

    @Column(name = "quantite")
    private int quantite;

    // Getters and setters
}

