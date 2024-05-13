package com.onlycamsbackend.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PanierService {

    @Autowired
    private static JdbcTemplate jdbcTemplate;

    public static void getBasket(int id_user){
        jdbcTemplate.update("CALL GetItemsInCart(?)",id_user);
    }
    public void addProductToBasket(int id_user,int id_basket,int quantity){
        jdbcTemplate.update("CALL AjouterProduitAuPanier(?, ?, ?)",id_user,id_basket,quantity);
    }

    public void removeProductFromBasket(int id_user,int id_basket,int quantity){
        jdbcTemplate.update("CALL RetirerDuPanier(?, ?, ?)",id_user,id_basket,quantity);
    }
}
