package com.example.labpokemons.controllers;

import com.example.labpokemons.models.Food;
import com.example.labpokemons.models.MyPokemon;
import com.example.labpokemons.services.FoodService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class FoodController {
    private final FoodService foodService;

    public FoodController(final FoodService foodServ) {
        this.foodService = foodServ;
    }

    @GetMapping("/getFood/{name}")
    public Food getFood(@PathVariable(name = "name") final String name) {
        return foodService.searchByName(name);
    }

    @PostMapping("/postFood/{id}")
    public void insertAbility(@RequestBody final Food food,
                              @PathVariable(name = "id") final List<Long> id) {
        foodService.insertFood(food, id);
    }

    @DeleteMapping("/deleteFood/{id}")
    public void deleteAbility(@PathVariable(name = "id") final Long id) {
        foodService.deleteFoodById(id);
    }

    @PutMapping("/updateFood/{id}")
    public void updateAbility(@RequestBody final Food food,
                              @PathVariable(name = "id") final Long id) {
        foodService.updateFood(food, id);
    }

    @GetMapping("/getFoodPokemons/{id}")
    public Set<MyPokemon> getPokemons(@PathVariable(name = "id") final Long id) {
        return foodService.getPokemons(id);
    }

    @GetMapping("/getAllFood")
    public List<Food> getALL() {
        return foodService.getALL();
    }
}
