package com.onlycamsbackend.demo.service;

import com.onlycamsbackend.demo.model.ProduitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProduitService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ProduitDTO getProduitInfo(int productId) {
        String query = "SELECT * FROM Produit WHERE Product_Id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{productId}, (rs, rowNum) -> {
            ProduitDTO produitDTO = new ProduitDTO();
            produitDTO.setProductId(rs.getInt("Product_Id"));
            produitDTO.setNom(rs.getString("Nom"));
            produitDTO.setPrenom(rs.getString("Prenom"));
            produitDTO.setDescription(rs.getString("Description"));
            produitDTO.setPrix(rs.getDouble("Prix"));
            produitDTO.setStock(rs.getInt("Stock"));
            produitDTO.setCategorie(rs.getString("Categorie"));
            produitDTO.setPourcentagePromotion(rs.getDouble("Pourcentage_Promotion"));
            return produitDTO;
        });
    }

    public void addStockToProduct(int id_product,int quantity){
        jdbcTemplate.update("CALL AugmenterStockProduit(?, ?)",id_product,quantity);
    }

    public void modifyProductReduction(int id_product, int reduction){
        jdbcTemplate.update("CALL ModifierReductionProduit(?, ?)",id_product,reduction);
    }

    public void removeZeroStockProduct(int id_product){
        jdbcTemplate.update("CALL SupprimerProduitStockZero(?)",id_product);
    }

    public int getNumberOfLike(int productId) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM aime WHERE Product_Id = ?", new Object[]{productId}, Integer.class);
    }

    public boolean hasLiked(int userId, int productId) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM aime WHERE Utilisateur_id = ? AND Product_Id = ?", new Object[]{userId, productId}, Integer.class) > 0;
    }
}
