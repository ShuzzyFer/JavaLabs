package com.example.labpokemons.controllers;

import com.example.labpokemons.models.Ability;
import com.example.labpokemons.models.MyPokemon;
import com.example.labpokemons.services.AbilityService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class AbilityController {
    private final AbilityService abilityService;
    public AbilityController(AbilityService abilityService) {
        this.abilityService = abilityService;
    }
    @GetMapping("/getAbility/{name}")
    public List<MyPokemon> getPokemon(@PathVariable(name="name") String name) throws IOException {
        return abilityService.searchByName(name);
    }
    @PostMapping("/postAbility")
    public void insertPokemon(@RequestBody Ability ability) {
        abilityService.insertAbility(ability);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteAbility(@PathVariable(name="id") Long id) {
        abilityService.deleteAbilityById(id);
    }
    @PutMapping("/update/{id}")
    public void updateAbility(@RequestBody Ability ability, @PathVariable(name="id") Long id) {
        abilityService.updateAbility(ability, id);
    }
}
