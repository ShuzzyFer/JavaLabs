package com.example.labpokemons.controllers;

import com.example.labpokemons.models.Ability;
import com.example.labpokemons.services.AbilityService;
import com.example.labpokemons.services.RequestCounterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class AbilityController {

    private final AbilityService abilityService;
    private final RequestCounterService requestCounterService;

    @GetMapping("/getAbility/{name}")
    public List<Ability> getAbility(@PathVariable(name = "name") final String name) {
        requestCounterService.increment();
        return abilityService.searchByName(name);
    }

    @PostMapping("/postAbility/{id}")
    public void insertAbility(@RequestBody final Ability ability,
                              @PathVariable(name = "id") final Long id) {
        requestCounterService.increment();
        abilityService.insertAbility(ability, id);
    }

    @DeleteMapping("/deleteAbility/{id}")
    public void deleteAbility(@PathVariable(name = "id") final Long id) {
        requestCounterService.increment();
        abilityService.deleteAbilityById(id);
    }

    @PutMapping("/updateAbility/{id}")
    public void updateAbility(@RequestBody final Ability ability,
                              @PathVariable(name = "id") final Long id) {
        requestCounterService.increment();
        abilityService.updateAbility(ability, id);
    }

    @GetMapping("/getPokemonAbilities/{name}")
    public List<Ability> getAbilities(@PathVariable(name = "name") final String name) {
        requestCounterService.increment();
        return abilityService.searchByPokemonName(name);
    }

    @GetMapping("/get-counter")
    public ResponseEntity<Integer> getCounter(){
        return ResponseEntity.ok(requestCounterService.get());
    }

    @GetMapping("/reset-counter")
    public ResponseEntity<String> resetCounter(){
        requestCounterService.reset();
        return ResponseEntity.ok("Request counter is successfully reset");
    }
}
