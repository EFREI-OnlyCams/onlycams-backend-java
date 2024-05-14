package com.onlycamsbackend.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;

@Service
public class CommandeService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void passCommand(int user_id, int product_id, int quantity) {
        jdbcTemplate.update("CALL PasserCommande(?, ?, ?)", user_id, product_id, quantity);
    }

    public List<Map<String, Object>> getOrdersByUserId(int userId) {
        return jdbcTemplate.queryForList("SELECT * FROM commande WHERE Utilisateur_Id = ?", userId);
    }

}
