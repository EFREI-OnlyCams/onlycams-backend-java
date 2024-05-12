package com.onlycamsbackend.demo.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class PanierId implements Serializable {

    @ManyToOne
    private Utilisateur utilisateur;

    @ManyToOne
    private Produit produit;

    // Getters and setters
}
