package com.example.labpokemons.controllers;

import com.example.labpokemons.models.Food;
import com.example.labpokemons.models.MyPokemon;
import com.example.labpokemons.services.FoodService;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

@RestController
public class FoodController {
    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/getFood/{name}")
    public Food getFood(@PathVariable(name = "name") String name) {
        return foodService.searchByName(name);
    }

    @PostMapping("/postFood/{id}")
    public void insertAbility(@RequestBody Food food, @PathVariable(name = "id") List<Long> id) throws NoSuchAlgorithmException {
        foodService.insertFood(food, id);
    }

    @DeleteMapping("/deleteFood/{id}")
    public void deleteAbility(@PathVariable(name = "id") Long id) {
        foodService.deleteFoodById(id);
    }

    @PutMapping("/updateFood/{id}")
    public void updateAbility(@RequestBody Food food, @PathVariable(name = "id") Long id) {
        foodService.updateFood(food, id);
    }

    @GetMapping("/getFoodPokemons/{id}")
    public Set<MyPokemon> getPokemons(@PathVariable(name = "id") Long id) {
        return foodService.getPokemons(id);
    }
}
