package com.example.labpokemons.controllers;

import com.example.labpokemons.models.Food;
import com.example.labpokemons.models.MyPokemon;
import com.example.labpokemons.services.PokemonService;
import com.example.labpokemons.services.RequestCounterService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@RestController
public class PokeController {
    private final PokemonService pokemonService;
    private final RequestCounterService requestCounterService;

    @Autowired
    PokeController(final PokemonService pokemonServ, RequestCounterService requestCounterService) {
        this.pokemonService = pokemonServ;
        this.requestCounterService = requestCounterService;
    }

    @Transactional
    @GetMapping("/pokemons/{params}")
    public List<MyPokemon> getPokemons(@PathVariable(name = "params") final String name) throws IOException {
        requestCounterService.increment();
        return pokemonService.getPokemonsListByParams(name);
    }

    @GetMapping("/getPokemon/{name}")
    public List<MyPokemon> getPokemon(@PathVariable(name = "name") final String name) {
        requestCounterService.increment();
        return pokemonService.searchByName(name);
    }

    @PostMapping("/postPokemon")
    public void insertPokemon(@RequestBody final MyPokemon pokemon) {
        requestCounterService.increment();
        pokemonService.insertPokemon(pokemon);
    }

    @PostMapping("/postPokemons/bulk")
    public void bulkUpdateParameters(@RequestBody final MyPokemon[] parameters) {
        requestCounterService.increment();
        Stream.of(parameters).forEach(pokemonService::insertPokemon);
    }

    @DeleteMapping("/deletePokemon/{id}")
    public void deletePokemon(@PathVariable(name = "id") final Long id) {
        requestCounterService.increment();
        pokemonService.deletePokemonById(id);
    }

    @PutMapping("/updatePokemon/{id}")
    public void updatePokemon(@RequestBody final MyPokemon pokemon,
                              @PathVariable(name = "id") final Long id) {
        requestCounterService.increment();
        pokemonService.updatePokemon(pokemon, id);
    }

    @GetMapping("/getPokemonFood/{id}")
    public Set<Food> getFood(@PathVariable(name = "id") final Long id) {
        requestCounterService.increment();
        return pokemonService.getFood(id);
    }

    @GetMapping("/getAllPokemons")
    public List<MyPokemon> getALL() {
        requestCounterService.increment();
        return pokemonService.getALL();
    }
}
