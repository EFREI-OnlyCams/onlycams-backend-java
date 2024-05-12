package com.onlycamsbackend.demo.repository;

import com.onlycamsbackend.demo.model.Aime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AimeRepository extends JpaRepository<Aime, Integer> {
    // You can add custom query methods here if needed
}
