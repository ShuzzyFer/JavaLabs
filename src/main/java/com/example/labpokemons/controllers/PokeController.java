package com.example.labpokemons.controllers;

import com.example.labpokemons.models.Pokemon;
import com.example.labpokemons.repositories.PokemonRepository;
import com.example.labpokemons.services.OkHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;

@RestController
public class PokeController {
    private final PokemonRepository pokemonRepository;
    @Autowired
    PokeController(PokemonRepository pokemonRepository){
        this.pokemonRepository=pokemonRepository;
    }
    @GetMapping("/pokemons/{params}")
    public List<Pokemon> getPokemons(@PathVariable(name = "params") String name) throws IOException {
        Pokemon pokemon=OkHttpRequest.get(name).getPokemons().get(0);
        pokemonRepository.save(pokemon);
        return OkHttpRequest.get(name).getPokemons();
    }
    @GetMapping("/pokemons")
    public List<Pokemon> getPokemons() throws IOException {
        return OkHttpRequest.get("").getPokemons();
    }
}