package com.onlycamsbackend.demo.model;

import com.onlycamsbackend.demo.model.PanierId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Panier")
public class Panier {

    @EmbeddedId
    private PanierId id;

    @Column(name = "quantite")
    private int quantite;

    // Getters and setters
}
