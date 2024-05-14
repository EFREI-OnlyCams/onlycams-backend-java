package com.onlycamsbackend.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PanierService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> getBasket(int id_user) {
        List<Map<String, Object>> basket = jdbcTemplate.queryForList("CALL GetItemsInCart(?)", id_user);
        System.out.println(basket);

        List<String> productIds = new ArrayList<>();

        for (Map<String, Object> item : basket) {
            String productId = String.valueOf(item.get("Product_Id")); // Correct key name
            productIds.add(productId);
        }

        System.out.println(productIds);
        return productIds;
    }


    public void addProductToBasket(int id_user, int id_basket, int quantity) {
        jdbcTemplate.update("CALL AjouterProduitAuPanier(?, ?, ?)", id_user, id_basket, quantity);
    }

    public void removeProductFromBasket(int id_user, int id_basket, int quantity) {
        jdbcTemplate.update("CALL RetirerDuPanier(?, ?, ?)", id_user, id_basket, quantity);
    }
}
