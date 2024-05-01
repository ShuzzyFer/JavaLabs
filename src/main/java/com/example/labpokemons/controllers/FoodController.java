package com.example.labpokemons.controllers;

import com.example.labpokemons.models.Food;
import com.example.labpokemons.models.MyPokemon;
import com.example.labpokemons.services.FoodService;
import com.example.labpokemons.services.RequestCounterService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
public class FoodController {
    private final FoodService foodService;
    private final RequestCounterService requestCounterService;

    @GetMapping("/getFood/{name}")
    public Food getFood(@PathVariable(name = "name") final String name) {
        requestCounterService.increment();
        return foodService.searchByName(name);
    }

    @PostMapping("/postFood/{id}")
    public void insertAbility(@RequestBody final Food food,
                              @PathVariable(name = "id") final List<Long> id) {
        requestCounterService.increment();
        foodService.insertFood(food, id);
    }

    @DeleteMapping("/deleteFood/{id}")
    public void deleteAbility(@PathVariable(name = "id") final Long id) {
        requestCounterService.increment();
        foodService.deleteFoodById(id);
    }

    @PutMapping("/updateFood/{id}")
    public void updateAbility(@RequestBody final Food food,
                              @PathVariable(name = "id") final Long id) {
        requestCounterService.increment();
        foodService.updateFood(food, id);
    }

    @GetMapping("/getFoodPokemons/{id}")
    public Set<MyPokemon> getPokemons(@PathVariable(name = "id") final Long id) {
        requestCounterService.increment();
        return foodService.getPokemons(id);
    }

    @GetMapping("/getAllFood")
    public List<Food> getALL() {
        requestCounterService.increment();
        return foodService.getALL();
    }
}
