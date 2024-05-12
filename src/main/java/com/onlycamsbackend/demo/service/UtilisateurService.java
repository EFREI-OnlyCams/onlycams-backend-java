package com.onlycamsbackend.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createUser(String nom, String prenom, String email, String numeroTel, String motDePasse, boolean estValide, boolean estAdmin, int note, String cheminAvatar, String adresse) {
        jdbcTemplate.update("CALL CreerUtilisateur(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", nom, prenom, email, numeroTel, motDePasse, estValide, estAdmin, note, cheminAvatar, adresse);
    }
}
