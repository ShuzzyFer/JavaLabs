package com.example.labpokemons.services;

import com.example.labpokemons.models.Food;
import com.example.labpokemons.repositories.FoodRepository;
import com.example.labpokemons.repositories.PokemonRepository;
import org.springframework.stereotype.Service;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

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
}
