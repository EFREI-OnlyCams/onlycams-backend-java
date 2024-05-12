package com.onlycamsbackend.demo;

import jakarta.persistence.*;
@Entity
@Table(name = "Produit")
public class Produit {
    @Id
    @Column(name = "Product_Id")
    private int productId;

    @Column(name = "Nom")
    private String nom;

    @Column(name = "Prenom")
    private String prenom;

    @Column(name = "Description")
    private String description;

    @Column(name = "Prix")
    private double prix;

    @Column(name = "Stock")
    private int stock;

    @Column(name = "Categorie")
    private String categorie;

    @Column(name = "Pourcentage_Promotion")
    private double pourcentagePromotion;

    // Getters and setters
}

