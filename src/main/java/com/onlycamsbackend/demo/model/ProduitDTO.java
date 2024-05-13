package com.onlycamsbackend.demo.model;

public class ProduitDTO {
    private int productId;
    private String nom;
    private String prenom;
    private String description;
    private double prix;
    private int stock;
    private String categorie;
    private double pourcentagePromotion;

    // Constructeur par défaut
    public ProduitDTO() {
    }

    // Getters et Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public double getPourcentagePromotion() {
        return pourcentagePromotion;
    }

    public void setPourcentagePromotion(double pourcentagePromotion) {
        this.pourcentagePromotion = pourcentagePromotion;
    }

    // Méthode toString pour afficher les informations du produit
    @Override
    public String toString() {
        return "ProduitDTO{" +
                "productId=" + productId +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", stock=" + stock +
                ", categorie='" + categorie + '\'' +
                ", pourcentagePromotion=" + pourcentagePromotion +
                '}';
    }
}

