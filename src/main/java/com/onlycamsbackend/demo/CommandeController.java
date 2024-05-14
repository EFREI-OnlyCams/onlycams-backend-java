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

    @GetMapping("/commandes/{userId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Map<String, Object>> getOrdersByUserId(@PathVariable int userId) {
        return commandeService.getOrdersByUserId(userId);
    }

}