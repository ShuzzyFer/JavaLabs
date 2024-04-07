package com.example.labpokemons.controllers;

import com.example.labpokemons.models.Food;
import com.example.labpokemons.models.MyPokemon;
import com.example.labpokemons.services.PokemonService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
public class PokeController {
    private final PokemonService pokemonService;


    @Autowired
    PokeController(final PokemonService pokemonServ) {
        this.pokemonService = pokemonServ;
    }

    @Transactional
    @GetMapping("/pokemons/{params}")
    public List<MyPokemon> getPokemons(@PathVariable(name = "params") final String name) throws IOException {
        return pokemonService.getPokemonsListByParams(name);
    }

    @GetMapping("/getPokemon/{name}")
    public List<MyPokemon> getPokemon(@PathVariable(name = "name") final String name) {
        return pokemonService.searchByName(name);
    }

    @PostMapping("/postPokemon")
    public void insertPokemon(@RequestBody final MyPokemon pokemon) {
        pokemonService.insertPokemon(pokemon);
    }

    @DeleteMapping("/deletePokemon/{id}")
    public void deletePokemon(@PathVariable(name = "id") final Long id) {
        pokemonService.deletePokemonById(id);
    }

    @PutMapping("/updatePokemon/{id}")
    public void updatePokemon(@RequestBody final MyPokemon pokemon,
                              @PathVariable(name = "id") final Long id) {
        pokemonService.updatePokemon(pokemon, id);
    }

    @GetMapping("/getPokemonFood/{id}")
    public Set<Food> getFood(@PathVariable(name = "id") final Long id) {
        return pokemonService.getFood(id);
    }

    @GetMapping("/getAllPokemons")
    public List<MyPokemon> getALL() {
        return pokemonService.getALL();
    }
}
