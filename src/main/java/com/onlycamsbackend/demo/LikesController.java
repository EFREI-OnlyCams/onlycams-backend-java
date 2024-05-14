package com.onlycamsbackend.demo;

import com.onlycamsbackend.demo.model.Utilisateur;
import com.onlycamsbackend.demo.model.UtilisateurDTO;
import com.onlycamsbackend.demo.service.PanierService;
import com.onlycamsbackend.demo.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikesController {

    @Autowired
    private ProduitService produitService;


    @GetMapping("/getLikes/{productId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Integer> getUserInfo(@PathVariable int productId) {
        int likes = produitService.getNumberOfLike(productId);
        return ResponseEntity.ok().body(likes);
    }

    @PostMapping("/{userId}/hasLiked/{productId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Boolean> hasLike(@PathVariable int userId,@PathVariable int productId) {
        boolean like = produitService.hasLiked(userId,productId);
        return ResponseEntity.ok().body(like);
    }
}
