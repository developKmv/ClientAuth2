package ru.client.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.client.models.Ingredient;
import ru.client.restRequest.Client;

import java.util.List;

@Controller
@RequestMapping("/")
@Slf4j
public class MainController {
    @Autowired
    public Client client;
    @GetMapping
    public String getHomePage(){
        return "home";
    }
    @GetMapping("/getIngredients")
    public String getIngredients(Model model){
        List<Ingredient> ingArr = client.getIngredientList();
        model.addAttribute("ingArr",ingArr);
        return "home";
    }
    @ModelAttribute(name = "ing")
    public Ingredient createIngredient(){
        Ingredient ing = new Ingredient();
        log.info("create ingredient:{}",ing);
        return ing;
    }
    @PostMapping("/addIngredient")
    public String addIngredient(@ModelAttribute(name ="ing") Ingredient ingredient){
        client.addIngredient(ingredient);
        return "home";
    }
    @PostMapping("/deleteIngredient")
    public String deleteIngredient(@RequestParam(name = "id") String id){
        log.info("delete ingredient id:{}",id);
        client.deleteIngredient(id);
        return "home";
    }
}
