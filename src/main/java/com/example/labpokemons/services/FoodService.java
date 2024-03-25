package com.example.labpokemons.services;

import com.example.labpokemons.cache.EntityCache;
import com.example.labpokemons.models.Food;
import com.example.labpokemons.models.MyPokemon;
import com.example.labpokemons.repositories.FoodRepository;
import com.example.labpokemons.repositories.PokemonRepository;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FoodService {
    private final FoodRepository foodRepository;
    private final PokemonRepository pokemonRepository;
    private final EntityCache<String, Food> entityCache;

    public FoodService(FoodRepository foodRepository, PokemonRepository pokemonRepository,
                       EntityCache<String, Food> entityCache) {
        this.foodRepository = foodRepository;
        this.pokemonRepository = pokemonRepository;
        this.entityCache = entityCache;
    }

    public Food searchByName(String name) {
        Food food = entityCache.get(name);
        if (food == null) {
            food = foodRepository.searchByName(name);
            entityCache.put(food.getName(), food);
        }
        return foodRepository.searchByName(name);
    }

    public void insertFood(Food food, List<Long> ids) {
        if (foodRepository.searchByName(food.getName()) != null)
            return;
        for (int i = 0; i < ids.size(); i++) {
            food.getPokemons().add(pokemonRepository.searchById(ids.get(i)));
        }
        entityCache.put(food.getName(), food);
        foodRepository.save(food);
    }

    public void deleteFoodById(Long id) {
        Optional<Food> food = foodRepository.findById(id);
        if (food.isPresent()) {
            entityCache.remove(food.get().getName());
        }
        foodRepository.deleteById(id);
    }

    public void updateFood(Food food, Long id) {
        food.setId(id);
        Optional<Food> foodOld = foodRepository.findById(id);
        if (foodOld.isPresent()) {
            entityCache.remove(foodOld.get().getName());
        }
        entityCache.put(food.getName(), food);
        foodRepository.save(food);
    }

    public Set<MyPokemon> getPokemons(Long id) {
        Optional<Food> food = foodRepository.findById(id);
        if (food.isPresent())
            return food.get().getPokemons();
        else
            return new HashSet<>();
    }
}
