package com.onlycamsbackend.demo.repository;

import com.onlycamsbackend.demo.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepository extends JpaRepository<Commande, Integer> {
    // You can add custom query methods here if needed
}