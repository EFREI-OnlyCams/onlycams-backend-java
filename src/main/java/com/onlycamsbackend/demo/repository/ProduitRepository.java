package com.onlycamsbackend.demo.repository;

import com.onlycamsbackend.demo.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit, Integer> {
    // You can add custom query methods here if needed
}