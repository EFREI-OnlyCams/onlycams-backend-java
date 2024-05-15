package com.onlycamsbackend.demo.service;

import com.onlycamsbackend.demo.model.Utilisateur;
import com.onlycamsbackend.demo.model.UtilisateurDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UtilisateurService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createUser(String nom, String prenom, String email, String numeroTel, String motDePasse, int note, String adresse) {
        jdbcTemplate.update("CALL CreerUtilisateur(?, ?, ?, ?, ?, ?, ?, ?, ?)", nom, prenom, email, numeroTel, motDePasse, false, false, note, adresse);
    }
    public int verifyConnexion(String email, String motDePasse) {
        String query = "CALL VerifierUtilisateur(?, ?)";
        Integer userId = jdbcTemplate.queryForObject(query, new Object[]{email, motDePasse}, Integer.class);
        return userId != null ? userId : -1;
    }

    public UtilisateurDTO getUserInfo(int userId) {
        String query = "SELECT * FROM utilisateur WHERE utilisateur_id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{userId}, (rs, rowNum) -> {
            UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
            utilisateurDTO.setUtilisateurId(String.valueOf(rs.getInt("utilisateur_id")));
            utilisateurDTO.setNom(rs.getString("nom"));
            utilisateurDTO.setPrenom(rs.getString("prenom"));
            utilisateurDTO.setEmail(rs.getString("email"));
            utilisateurDTO.setNumeroTel(rs.getString("numero_tel"));
            utilisateurDTO.setEstValide(rs.getBoolean("est_valide"));
            utilisateurDTO.setEstAdmin(rs.getBoolean("est_admin"));
            utilisateurDTO.setNote(rs.getInt("note"));
            utilisateurDTO.setAdresse(rs.getString("adresse"));
            return utilisateurDTO;
        });
    }

    public List<UtilisateurDTO> getAllUserInfo() {
        String query = "SELECT * FROM utilisateur WHERE est_valide = false";
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
            utilisateurDTO.setUtilisateurId(String.valueOf(rs.getInt("utilisateur_id")));
            utilisateurDTO.setNom(rs.getString("nom"));
            utilisateurDTO.setPrenom(rs.getString("prenom"));
            utilisateurDTO.setEmail(rs.getString("email"));
            utilisateurDTO.setNumeroTel(rs.getString("numero_tel"));
            utilisateurDTO.setEstValide(rs.getBoolean("est_valide"));
            utilisateurDTO.setEstAdmin(rs.getBoolean("est_admin"));
            utilisateurDTO.setNote(rs.getInt("note"));
            utilisateurDTO.setAdresse(rs.getString("adresse"));
            return utilisateurDTO;
        });
    }



    public void changePassword(int id,String motDePasse) {
        jdbcTemplate.update("CALL ChangerMotDePasse(?, ?)",id,motDePasse);
    }

    public void updateUser(String nom, String prenom, String numeroTel, String motDePasse, int note, String adresse) {
        jdbcTemplate.update("CALL ModifierUtilisateur(?, ?, ?, ?, ?, ?)", nom, prenom, numeroTel, motDePasse, note, adresse);
    }

    public void noteWebsite(int id,int note ){
        jdbcTemplate.update("CALL NoterSite(?, ?)",id, note);
    }

    public void radiateUser(int id){
        jdbcTemplate.update("CALL RadierUtilisateur(?)",id);
    }

    public void validateUser(int id){
        jdbcTemplate.update("CALL ValiderUtilisateur(?)",id);
    }


}
