package com.example.labpokemons.services;

import com.example.labpokemons.models.Ability;
import com.example.labpokemons.models.Food;
import com.example.labpokemons.models.MyPokemon;
import com.example.labpokemons.repositories.FoodRepository;
import com.example.labpokemons.repositories.PokemonRepository;
import com.github.oscar0812.pokeapi.models.pokemon.Pokemon;
import com.github.oscar0812.pokeapi.utils.Client;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PokemonService {
    private final PokemonRepository pokemonRepository;
    private final AbilityService abilityService;
    private final FoodService foodService;
    private final FoodRepository foodRepository;
    public PokemonService(PokemonRepository pokemonRepository, AbilityService abilityService, FoodService foodService, FoodRepository foodRepository) {
        this.pokemonRepository = pokemonRepository;
        this.abilityService = abilityService;
        this.foodService = foodService;
        this.foodRepository = foodRepository;
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
        for(int i=0; i<pokemonRepository.searchById(id).getFood().size();i++) {
            Optional<MyPokemon> pok=pokemonRepository.findById(id);
            if (pok.isPresent()) {
                Optional<Food> foo=pokemonRepository.searchById(id).getFood().stream().findFirst();
                if(foo.isPresent()) {
                    Food food = pokemonRepository.searchById(id).getFood().stream().findFirst().get();
                    food.getPokemons().remove(pokemonRepository.searchById(id));
                    MyPokemon pokemon = pokemonRepository.searchById(id);
                    pokemon.getFood().remove(food);
                    updatePokemon(pokemon, pokemonRepository.searchById(id).getId());
                    if (food.getPokemons().isEmpty())
                        foodService.deleteFoodById(food.getId());
                    else
                        foodService.updateFood(food, food.getId());
                }
            }
        }
        pokemonRepository.deleteById(id);
    }
    public void updatePokemon(MyPokemon pokemon, Long id) {
        pokemon.setId(id);
        pokemonRepository.save(pokemon);
    }
}
