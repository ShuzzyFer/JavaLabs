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
    PokeController(final PokemonService pokemonServ, PokemonRepository pokemonRepository, RequestCounterService requestCounterService) {
        this.pokemonService = pokemonServ;
        this.pokemonRepository = pokemonRepository;
        this.requestCounterService = requestCounterService;
    }

//    @Transactional
//    @GetMapping("/pokemons/{params}")
//    public List<MyPokemon> getPokemons(@PathVariable(name = "params") final String name) throws IOException {
//        requestCounterService.increment();
//        return pokemonService.getPokemonsListByParams(name);
//    }
//
//    @GetMapping("/getPokemon/{name}")
//    public List<MyPokemon> getPokemon(@PathVariable(name = "name") final String name) {
//        requestCounterService.increment();
//        return pokemonService.searchByName(name);
//    }
//
//    @PostMapping("/postPokemon")
//    public void insertPokemon(@RequestBody final MyPokemon pokemon) {
//        requestCounterService.increment();
//        pokemonService.insertPokemon(pokemon);
//    }
//
//    @PostMapping("/postPokemons/bulk")
//    public void bulkUpdateParameters(@RequestBody final MyPokemon[] parameters) {
//        requestCounterService.increment();
//        Stream.of(parameters).forEach(pokemonService::insertPokemon);
//    }
//
//    @DeleteMapping("/deletePokemon/{id}")
//    public void deletePokemon1(@PathVariable(name = "id") final Long id) {
//        requestCounterService.increment();
//        pokemonService.deletePokemonById(id);
//    }
//
//    @PutMapping("/updatePokemon/{id}")
//    public void updatePokemon(@RequestBody final MyPokemon pokemon,
//                              @PathVariable(name = "id") final Long id) {
//        requestCounterService.increment();
//        pokemonService.updatePokemon(pokemon, id);
//    }
//
//    @GetMapping("/getPokemonFood/{id}")
//    public Set<Food> getFood(@PathVariable(name = "id") final Long id) {
//        requestCounterService.increment();
//        return pokemonService.getFood(id);
//    }

    @GetMapping("/viewAllPokemons")
    public String viewAllPokemons(Model model) {
        List<MyPokemon> pokemons = pokemonService.getALL();
        model.addAttribute("pokemons", pokemons);
        return "viewAllPokemons";
    }

    @GetMapping("/addPokemon")
    public String addPokemonForm() {
        return "addPokemon";
    }

    @PostMapping("/postPokemon")
    public String addPokemon(@RequestParam String name, @RequestParam String url) {
        MyPokemon pokemon = new MyPokemon();
        pokemon.setName(name);
        pokemon.setUrl(url);
        pokemonService.insertPokemon(pokemon);
        return "redirect:/viewAllPokemons";
    }

    @GetMapping("/deletePokemon/{id}")
    public String deletePokemon(@PathVariable Long id) {
        pokemonService.deletePokemonById(id);
        return "redirect:/viewAllPokemons";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/searchPokemon")
    public String searchPokemon(@RequestParam String name, Model model) {
        List<MyPokemon> pokemons = pokemonService.searchByName(name);
        model.addAttribute("pokemons", pokemons);
        return "searchResults";
    }

    @GetMapping("/updatePokemonForm")
    public String updatePokemonForm(@RequestParam(name = "id") Long id, Model model) {
        MyPokemon pokemon = pokemonRepository.searchById(id);
        model.addAttribute("pokemon", pokemon);
        return "updatePokemonForm";
    }

    @PostMapping("/updatePokemon")
    public String updatePokemon(@ModelAttribute MyPokemon pokemon, @RequestParam Long id) {
        pokemonService.updatePokemon(pokemon, id);
        return "redirect:/viewAllPokemons";
    }
}
