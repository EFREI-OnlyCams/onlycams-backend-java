package com.onlycamsbackend.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

@Service
public class CommandeService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void passCommand(int user_id, int product_id, int quantity) {
        jdbcTemplate.update("CALL PasserCommande(?, ?, ?)", user_id, product_id, quantity);
    }

    public List<String> getOrdersByUserId(int userId) {
        List<Map<String, Object>> Commande = jdbcTemplate.queryForList("SELECT Product_Id FROM commande WHERE Utilisateur_Id = ?", userId);

        List<String> productIds = new ArrayList<>();

        for (Map<String, Object> item : Commande) {
            String productId = String.valueOf(item.get("Product_Id")); // Correct key name
            productIds.add(productId);
        }
        System.out.println(productIds);
        return productIds;
    }

    public void OrderProducts(int id_user) {
        jdbcTemplate.update("CALL PasserCommande(?)", id_user);
        jdbcTemplate.update("CALL RetirerDuPanier(?)", id_user);

    }

}
