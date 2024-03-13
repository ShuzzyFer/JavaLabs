package com.example.labpokemons.services;

import com.example.labpokemons.models.Food;
import com.example.labpokemons.models.MyPokemon;
import com.example.labpokemons.repositories.FoodRepository;
import com.example.labpokemons.repositories.PokemonRepository;
import org.springframework.stereotype.Service;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FoodService {
    private final FoodRepository foodRepository;
    private final PokemonRepository pokemonRepository;

    public FoodService(FoodRepository foodRepository, PokemonRepository pokemonRepository) {
        this.foodRepository = foodRepository;
        this.pokemonRepository = pokemonRepository;
    }

    public Food searchByName(String name) {
        return foodRepository.searchByName(name);
    }
    public void insertFood(Food food, List<Long> ids) throws NoSuchAlgorithmException {
        for(int i=0; i< ids.size(); i++) {
            food.getPokemons().add(pokemonRepository.searchById(ids.get(i)));
        }
        int min=1;
        int max=100000;
        Long iden;
        while(true) {
            iden = (long) ((SecureRandom.getInstanceStrong().nextDouble() * ++max) + min);
            if(!foodRepository.findById(iden).isPresent())
                break;
        }
        food.setId(iden);
        foodRepository.save(food);
    }
    public void deleteFoodById(Long id) {
        foodRepository.deleteById(id);
    }
    public void updateFood(Food food, Long id) {
        food.setId(id);
        foodRepository.save(food);
    }

    public Set<MyPokemon> getPokemons(Long id) {
        Optional<Food> food=foodRepository.findById(id);
        if(food.isPresent())
            return foodRepository.findById(id).get().getPokemons();
        else {
            Set<MyPokemon> pokemons= new HashSet<>();
            return pokemons;
        }
    }
}
