package com.example.labpokemons.services;

import com.example.labpokemons.cache.EntityCache;
import com.example.labpokemons.exceptions.BadRequestException;
import com.example.labpokemons.exceptions.NotFoundException;
import com.example.labpokemons.exceptions.ServerException;
import com.example.labpokemons.models.Food;
import com.example.labpokemons.models.MyPokemon;
import com.example.labpokemons.repositories.FoodRepository;
import com.example.labpokemons.repositories.PokemonRepository;
import org.springframework.stereotype.Service;


import java.util.*;

import static com.example.labpokemons.utilities.Constants.*;

@Service
public class FoodService {
    private final FoodRepository foodRepository;
    private final PokemonRepository pokemonRepository;
    private final EntityCache<String, Food> entityCache;

    public FoodService(final FoodRepository foodRep,
                       final PokemonRepository pokemonRep,
                       final EntityCache<String, Food> entityC) {
        this.foodRepository = foodRep;
        this.pokemonRepository = pokemonRep;
        this.entityCache = entityC;
    }

    public Food searchByName(final String name) {
        if (name == null || name.equals(" ")) {
            throw new BadRequestException(INVALID_INFO_MSG);
        } else {
            try {
                Food food = entityCache.get(name);
                if (food == null) {
                    food = foodRepository.searchByName(name);
                    entityCache.put(food.getName(), food);
                }
                if (!food.getName().isEmpty()) {
                    return food;
                }
            } catch (Exception e) {
                throw new ServerException(SERVER_ERROR_MSG);
            }
            throw new NotFoundException(NOT_FOUND_MSG);
        }
    }

    public void insertFood(final Food food, final List<Long> ids) {
        try {
            if (foodRepository.searchByName(food.getName()) != null) {
                return;
            }
        } catch (Exception e) {
            throw new ServerException(SERVER_ERROR_MSG);
        }
        try {
            for (Long id : ids) {
                if (pokemonRepository.findById(id).isPresent()) {
                    food.getPokemons().add(pokemonRepository.searchById(id));
                } else {
                    food.getPokemons().clear();
                    break;
                }
            }
            entityCache.put(food.getName(), food);
            foodRepository.save(food);
        } catch (Exception e) {
            throw new ServerException(SERVER_ERROR_MSG);
        }
        if (food.getPokemons().isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MSG);
        }
    }

    public void deleteFoodById(final Long id) {
        Optional<Food> food = foodRepository.findById(id);
        if (food.isPresent()) {
            entityCache.remove(food.get().getName());
        } else {
            throw new BadRequestException(INVALID_INFO_MSG);
        }
        try {
            foodRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServerException(SERVER_ERROR_MSG);
        }
    }

    public void updateFood(final Food food, final Long id) {
        if (!foodRepository.findById(id).isPresent()) {
            throw new NotFoundException(NOT_FOUND_MSG);
        } else {
            try {
                food.setId(id);
                Optional<Food> foodOld = foodRepository.findById(id);
                if (foodOld.isPresent()) {
                    entityCache.remove(foodOld.get().getName());
                }
                entityCache.put(food.getName(), food);
                foodRepository.save(food);
            } catch (Exception e) {
                throw new ServerException(SERVER_ERROR_MSG);
            }
        }
    }

    public Set<MyPokemon> getPokemons(final Long id) {
        Optional<Food> food = foodRepository.findById(id);
        if (food.isPresent()) {
            try {
                return food.get().getPokemons();
            } catch (Exception e) {
                throw new ServerException(SERVER_ERROR_MSG);
            }
        } else {
            throw new NotFoundException(NOT_FOUND_MSG);
        }
    }

    public List<Food> getALL() {
        int counter = foodRepository.findAll().size();
        List<Food> foodList = new ArrayList<>();
        for (int i = 0; i < counter; i++) {
            try {
                Food food = foodRepository.findAll().get(i);
                entityCache.put(food.getName(), food);
                foodList.add(food);
            } catch (Exception e) {
                throw new ServerException(SERVER_ERROR_MSG);
            }
        }
        return foodList;
    }
}
