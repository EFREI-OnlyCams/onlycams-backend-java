package com.onlycamsbackend.demo;

import com.onlycamsbackend.demo.service.PanierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/basket")
public class PanierController {

    @Autowired
    private PanierService panierService;

    @GetMapping("/get/{userId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<String> getBasket(@PathVariable int userId) {
        return panierService.getBasket(userId);
    }

    @PostMapping("/add/{userId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void addProductToBasket(@PathVariable int userId, @RequestBody Map<String, Object> body) {
        int productId = (int) body.get("productId");
        int quantity = (int) body.get("quantity");
        panierService.addProductToBasket(userId, productId, quantity);
    }



}
