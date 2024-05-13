package com.onlycamsbackend.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void likeProduct(int id_user, int id_product){
        jdbcTemplate.update("CALL LikerProduit(?, ?)",id_user,id_product);
    }

    public void dislikeProduct(int id_user,int id_product){
        jdbcTemplate.update("CALL SupprimerLikeProduit(?, ?)",id_user,id_product);
    }
}
