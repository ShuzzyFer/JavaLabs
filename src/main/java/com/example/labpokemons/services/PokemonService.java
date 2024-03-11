package com.example.labpokemons.services;

import com.example.labpokemons.models.Ability;
import com.example.labpokemons.models.MyPokemon;
import com.example.labpokemons.repositories.PokemonRepository;
import com.github.oscar0812.pokeapi.models.pokemon.Pokemon;
import com.github.oscar0812.pokeapi.utils.Client;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PokemonService {
    private final PokemonRepository pokemonRepository;
    private final AbilityService abilityService;
    public PokemonService(PokemonRepository pokemonRepository, AbilityService abilityService) {
        this.pokemonRepository = pokemonRepository;
        this.abilityService = abilityService;
    }
    public List<MyPokemon> getPokemonsListByParams(String name) throws IOException, NoSuchAlgorithmException {
        List<MyPokemon> pokemons=OkHttpRequest.get(name).getPokemons();
        for(int j=0; j<pokemons.size(); j++)
        {
            List<Ability> abilities = new ArrayList<>();
            Pokemon pok = Client.getPokemonByName(pokemons.get(j).getName());
            MyPokemon pokemon = pokemons.get(j);
            pokemon.setAbilities(abilities);
            for (int i = 0; i < pok.getAbilities().size(); i++) {
                Ability ability= abilityService.parseAbility(pok, i);
                ability.setPokemon(pokemon);
                abilities.add(ability);
            }

            pokemonRepository.save(pokemon);
        }
        return pokemons;
    }

    public List<MyPokemon> searchByName(String name) {
        return pokemonRepository.searchByName(name);
    }
    public void insertPokemon(MyPokemon pokemon) {
        pokemonRepository.save(pokemon);
    }
    public void deletePokemonById(Long id) {
        pokemonRepository.deleteById(id);
    }
    public void updatePokemon(MyPokemon pokemon, Long id) {
        pokemon.setId(id);
        pokemonRepository.save(pokemon);
    }
}
