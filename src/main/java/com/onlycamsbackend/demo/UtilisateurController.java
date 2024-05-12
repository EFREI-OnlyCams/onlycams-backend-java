package com.onlycamsbackend.demo;

import com.onlycamsbackend.demo.model.Utilisateur;
import com.onlycamsbackend.demo.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UtilisateurController {

    @Autowired
    private UtilisateurService userService;

    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:3000")
    public void createUser(@RequestBody Utilisateur userRequest) {
        userService.createUser(userRequest.getNom(), userRequest.getPrenom(), userRequest.getEmail(), userRequest.getNumeroTel(), userRequest.getMotDePasse(), userRequest.getNote(), userRequest.getAdresse());
    }
}

