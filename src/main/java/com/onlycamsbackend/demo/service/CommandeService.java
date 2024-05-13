package com.onlycamsbackend.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CommandeService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void passCommand(int user_id,int product_id,int quantity){
        jdbcTemplate.update("CALL PasserCommande(?, ?, ?)",user_id,product_id,quantity);
    }
}
