package com.onlycamsbackend.demo.model;

public class UtilisateurDTO {
    private String utilisateurId;
    private String nom;
    private String prenom;
    private String email;
    private String numeroTel;
    private boolean estValide;
    private boolean estAdmin;
    private int note;
    private String adresse;

    // Constructeur par défaut
    public UtilisateurDTO() {
    }

    // Getters et Setters
    public String getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(String utilisateurId) {
        this.utilisateurId = utilisateurId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroTel() {
        return numeroTel;
    }

    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }

    public boolean isEstValide() {
        return estValide;
    }

    public void setEstValide(boolean estValide) {
        this.estValide = estValide;
    }

    public boolean isEstAdmin() {
        return estAdmin;
    }

    public void setEstAdmin(boolean estAdmin) {
        this.estAdmin = estAdmin;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    // Méthode toString pour afficher les informations de l'utilisateur
    @Override
    public String toString() {
        return "UtilisateurDTO{" +
                "utilisateurId=" + utilisateurId +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", numeroTel='" + numeroTel + '\'' +
                ", estValide=" + estValide +
                ", estAdmin=" + estAdmin +
                ", note=" + note +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}
