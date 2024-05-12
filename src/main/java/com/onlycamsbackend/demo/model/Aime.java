package com.onlycamsbackend.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Aime")
public class Aime {

    @EmbeddedId
    private AimeId id;

    // Getters and setters
}
