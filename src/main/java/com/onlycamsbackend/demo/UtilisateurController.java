package com.onlycamsbackend.demo;

import com.onlycamsbackend.demo.model.Utilisateur;
import com.onlycamsbackend.demo.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UtilisateurController {

    @Autowired
    private UtilisateurService userService;

    @PostMapping("/create")
    public void createUser(@RequestBody Utilisateur userRequest) {
        userService.createUser(userRequest.getNom(), userRequest.getPrenom(), userRequest.getEmail(), userRequest.getNumeroTel(), userRequest.getMotDePasse(), userRequest.isEstValide(), userRequest.isEstAdmin(), userRequest.getNote(), userRequest.getCheminAvatar(), userRequest.getAdresse());
    }
}

