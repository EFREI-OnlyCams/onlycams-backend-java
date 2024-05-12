package com.onlycamsbackend.demo.repository;

import com.onlycamsbackend.demo.model.Panier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PanierRepository extends JpaRepository<Panier, Integer> {
    // You can add custom query methods here if needed
}