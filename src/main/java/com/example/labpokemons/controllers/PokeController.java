package com.example.labpokemons.controllers;

import com.example.labpokemons.models.Ability;
import com.example.labpokemons.models.MyPokemon;
import com.example.labpokemons.repositories.AbilityRepository;
import com.example.labpokemons.repositories.PokemonRepository;
import com.example.labpokemons.services.OkHttpRequest;
import com.github.oscar0812.pokeapi.models.pokemon.Pokemon;
import com.github.oscar0812.pokeapi.utils.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PokeController {
    private final PokemonRepository pokemonRepository;
    private final AbilityRepository abilityRepository;
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    PokeController(PokemonRepository pokemonRepository, AbilityRepository abilityRepository){
        this.pokemonRepository=pokemonRepository;
        this.abilityRepository = abilityRepository;
    }
    @Transactional
    @GetMapping("/pokemons/{params}")
    public List<MyPokemon> getPokemons(@PathVariable(name = "params") String name) throws IOException {
        MyPokemon pokemon=OkHttpRequest.get(name).getPokemons().get(0);
        List<Ability> abilities=new ArrayList<>();


        Pokemon pok= Client.getPokemonByName(pokemon.getName());
        for(int i=0; i<pok.getAbilities().size(); i++)
        {
            Ability ability=new Ability();
            ability.setDescription(pok.getAbilities().get(i).getAbility().getFlavorTextEntries().get(0).getFlavorText());
            ability.setName(pok.getAbilities().get(i).getAbility().getName());
            ability.setPokemon(pokemon);
            int minValue = 1;
            int maxValue = 50000;
            ability.setId((long)(minValue + (int) (Math.random() * (maxValue - minValue + 1))));
            System.out.println(ability.getId());
            abilities.add(ability);
        }
        pokemon.setAbilities(abilities);
        System.out.println(pokemon.getAbilities().get(0).getId());
        pokemonRepository.save(pokemon);
        abilityRepository.saveAll(abilities);
        return OkHttpRequest.get(name).getPokemons();
    }
    @GetMapping("/pokemons")
    public List<MyPokemon> getPokemons() throws IOException {
        return OkHttpRequest.get("").getPokemons();
    }
}