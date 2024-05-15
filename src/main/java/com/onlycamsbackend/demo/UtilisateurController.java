package com.onlycamsbackend.demo;

import com.onlycamsbackend.demo.model.Utilisateur;
import com.onlycamsbackend.demo.model.UtilisateurDTO;
import com.onlycamsbackend.demo.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/verify")
    @CrossOrigin(origins = "http://localhost:3000")
    public int verifyConnexion(@RequestBody Utilisateur userRequest) {
        return userService.verifyConnexion(userRequest.getEmail(),userRequest.getMotDePasse());
    }

    @GetMapping("/infosUser/{userId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<UtilisateurDTO> getUserInfo(@PathVariable int userId) {
        UtilisateurDTO utilisateurDTO = userService.getUserInfo(userId);
        if (utilisateurDTO != null) {
            return ResponseEntity.ok().body(utilisateurDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/allUsers")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<UtilisateurDTO>> getAllNonValidUsers() {
        List<UtilisateurDTO> utilisateurDTOList = userService.getAllUserInfo();
        if (utilisateurDTOList != null && !utilisateurDTOList.isEmpty()) {
            return ResponseEntity.ok().body(utilisateurDTOList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/validateUser/{userId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void validateUser(@PathVariable int userId) {
        userService.validateUser(userId);
    }



}

