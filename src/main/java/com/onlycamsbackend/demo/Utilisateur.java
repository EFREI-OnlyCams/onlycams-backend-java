package com.onlycamsbackend.demo;

import jakarta.persistence.*;

@Entity
@Table(name = "Utilisateur")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "utilisateur_id")
    private int utilisateurId;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "email")
    private String email;

    @Column(name = "numero_tel")
    private String numeroTel;

    @Column(name = "mot_de_passe")
    private String motDePasse;

    @Column(name = "est_valide")
    private boolean estValide;

    @Column(name = "est_admin")
    private boolean estAdmin;

    @Column(name = "note")
    private int note;

    @Column(name = "adresse")
    private String adresse;

    // Getters and setters
}

