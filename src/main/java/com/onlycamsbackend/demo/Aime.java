package com.onlycamsbackend.demo;

import jakarta.persistence.*;

@Entity
@Table(name = "Aime")
public class Aime {
    @Id
    @ManyToOne
    @JoinColumn(name = "Utilisateur_Id")
    private Utilisateur utilisateur;

    @Id
    @ManyToOne
    @JoinColumn(name = "Product_Id")
    private Produit produit;

    // Getters and setters
}
