package com.onlycamsbackend.demo;
import com.onlycamsbackend.demo.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/commandes")
public class CommandeController {
    @Autowired
    private CommandeService commandeService;

    @GetMapping("/get/{userId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<String> getOrdersByUserId(@PathVariable int userId) {
        return commandeService.getOrdersByUserId(userId);
    }

    @PostMapping("/order/{userId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void OrderProducts(@PathVariable int userId) {
        commandeService.OrderProducts(userId);
    }
}