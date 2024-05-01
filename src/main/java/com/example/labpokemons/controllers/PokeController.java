package com.example.labpokemons.controllers;

import com.example.labpokemons.models.MyPokemon;
import com.example.labpokemons.repositories.PokemonRepository;
import com.example.labpokemons.services.PokemonService;
import com.example.labpokemons.services.RequestCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PokeController {
    private final PokemonService pokemonService;

    private final PokemonRepository pokemonRepository;
    private final RequestCounterService requestCounterService;

    @Autowired
    PokeController(final PokemonService pokemonServ, final PokemonRepository pokemonRepository, final RequestCounterService requestCounterService) {
        this.pokemonService = pokemonServ;
        this.pokemonRepository = pokemonRepository;
        this.requestCounterService = requestCounterService;
    }

    @GetMapping("/viewAllPokemons")
    public String viewAllPokemons(Model model) {
        requestCounterService.increment();
        List<MyPokemon> pokemons = pokemonService.getALL();
        model.addAttribute("pokemons", pokemons);
        return "viewAllPokemons";
    }

    @GetMapping("/addPokemon")
    public String addPokemonForm() {
        requestCounterService.increment();
        return "addPokemon";
    }

    @PostMapping("/postPokemon")
    public String addPokemon(@RequestParam String name, @RequestParam String url) {
        requestCounterService.increment();
        MyPokemon pokemon = new MyPokemon();
        pokemon.setName(name);
        pokemon.setUrl(url);
        pokemonService.insertPokemon(pokemon);
        return "redirect:/viewAllPokemons";
    }

    @GetMapping("/deletePokemon/{id}")
    public String deletePokemon(@PathVariable Long id) {
        requestCounterService.increment();
        pokemonService.deletePokemonById(id);
        return "redirect:/viewAllPokemons";
    }

    @GetMapping("/")
    public String index() {
        requestCounterService.increment();
        return "index";
    }

    @GetMapping("/searchPokemon")
    public String searchPokemon(@RequestParam String name, Model model) {
        requestCounterService.increment();
        List<MyPokemon> pokemons = pokemonService.searchByName(name);
        model.addAttribute("pokemons", pokemons);
        return "searchResults";
    }

    @GetMapping("/updatePokemonForm")
    public String updatePokemonForm(@RequestParam(name = "id") Long id, Model model) {
        requestCounterService.increment();
        MyPokemon pokemon = pokemonRepository.searchById(id);
        model.addAttribute("pokemon", pokemon);
        return "updatePokemonForm";
    }

    @PostMapping("/updatePokemon")
    public String updatePokemon(@ModelAttribute MyPokemon pokemon, @RequestParam Long id) {
        requestCounterService.increment();
        pokemonService.updatePokemon(pokemon, id);
        return "redirect:/viewAllPokemons";
    }
}
