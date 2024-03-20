package com.example.labpokemons.controllers;

import com.example.labpokemons.models.Ability;
import com.example.labpokemons.services.AbilityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AbilityController {
    private final AbilityService abilityService;

    public AbilityController(AbilityService abilityService) {
        this.abilityService = abilityService;
    }

    @GetMapping("/getAbility/{name}")
    public List<Ability> getAbility(@PathVariable(name = "name") String name) {
        return abilityService.searchByName(name);
    }

    @PostMapping("/postAbility/{id}")
    public void insertAbility(@RequestBody Ability ability, @PathVariable(name = "id") Long id) {
        abilityService.insertAbility(ability, id);
    }

    @DeleteMapping("/deleteAbility/{id}")
    public void deleteAbility(@PathVariable(name = "id") Long id) {
        abilityService.deleteAbilityById(id);
    }

    @PutMapping("/updateAbility/{id}")
    public void updateAbility(@RequestBody Ability ability, @PathVariable(name = "id") Long id) {
        abilityService.updateAbility(ability, id);
    }
    @GetMapping("/getPokemonAbilities/{name}")
    public List<Ability> getAbilities(@PathVariable(name = "name") String name) {
        return abilityService.searchByPokemonName(name);
    }
}
