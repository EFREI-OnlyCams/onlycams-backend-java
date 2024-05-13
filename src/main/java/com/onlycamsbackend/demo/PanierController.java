package com.onlycamsbackend.demo;

import com.onlycamsbackend.demo.service.PanierService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
public class PanierController {

    @GetMapping("/basket/{userId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void getBasket(@PathVariable int userId) {
        PanierService.getBasket(userId);
    }

}
