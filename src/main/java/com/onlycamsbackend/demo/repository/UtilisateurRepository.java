package com.onlycamsbackend.demo.repository;

import com.onlycamsbackend.demo.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    // You can add custom query methods here if needed
}