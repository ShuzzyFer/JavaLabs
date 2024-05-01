package com.example.labpokemons.services;

import com.example.labpokemons.exceptions.BadRequestException;
import com.example.labpokemons.exceptions.NotFoundException;
import com.example.labpokemons.exceptions.ServerException;
import com.example.labpokemons.models.Ability;
import com.example.labpokemons.models.Food;
import com.example.labpokemons.models.MyPokemon;
import com.example.labpokemons.repositories.PokemonRepository;
import com.github.oscar0812.pokeapi.models.pokemon.Pokemon;
import com.github.oscar0812.pokeapi.utils.Client;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static com.example.labpokemons.utilities.Constants.*;

@Service
public class PokemonService {
    private final PokemonRepository pokemonRepository;
    private final AbilityService abilityService;
    private final FoodService foodService;


    public PokemonService(final PokemonRepository pokemonRep,
                          final AbilityService abilityServ,
                          final FoodService foodServ) {
        this.pokemonRepository = pokemonRep;
        this.abilityService = abilityServ;
        this.foodService = foodServ;
    }

    public List<MyPokemon> getPokemonsListByParams(final String name)
            throws IOException {
        List<MyPokemon> pokemons = OkHttpRequest.get(name).getResults();
        for (int j = 0; j < pokemons.size(); j++) {
            List<Ability> abilities = new ArrayList<>();
            Pokemon pok = Client.getPokemonByName(pokemons.get(j).getName());
            MyPokemon pokemon = pokemons.get(j);
            pokemon.setAbilities(abilities);
            for (int i = 0; i < pok.getAbilities().size(); i++) {
                Ability ability = abilityService.parseAbility(pok, i);
                ability.setPokemon(pokemon);
                abilities.add(ability);
            }

            pokemonRepository.save(pokemon);
        }
        return pokemons;
    }

    public List<MyPokemon> searchByName(final String name) {
        if (name == null || name.equals(" ")) {
            throw new BadRequestException(INVALID_INFO_MSG);
        } else {
            try {
                List<MyPokemon> result = pokemonRepository.findAll().stream()
                        .filter(tv -> tv.getName().contains(name)).toList();
                if (!result.isEmpty()) {
                    return result;
                }
            } catch (Exception e) {
                throw new ServerException(SERVER_ERROR_MSG);
            }
        }
        throw new NotFoundException(NOT_FOUND_MSG);
    }

    public void insertPokemon(final MyPokemon pokemon) {
        pokemon.setId((long) 5);
        if (pokemon.getName() == null || pokemon.getName().equals(" ")) {
            throw new BadRequestException(INVALID_INFO_MSG);
        }
        try {
            if (pokemon.getId() != null) {
                pokemonRepository.save(pokemon);
                return;
            }
        } catch (Exception e) {
            throw new ServerException(SERVER_ERROR_MSG);
        }
        throw new NotFoundException(NOT_FOUND_MSG);

    }

    public void deletePokemonById(final Long id) {
        if (!pokemonRepository.findById(id).isPresent()) {
            throw new NotFoundException(NOT_FOUND_MSG);
        }
        for (int i = 0; i < pokemonRepository.searchById(id)
                .getFood().size(); i++) {
            Optional<MyPokemon> pok = pokemonRepository.findById(id);
                try {
                    if (pok.isPresent()) {
                        Optional<Food> foo = pokemonRepository
                                .searchById(id)
                                .getFood()
                                .stream()
                                .findFirst();
                        if (foo.isPresent()) {
                            Food food = foo.get();
                            food.getPokemons()
                                    .remove(pokemonRepository.searchById(id));
                            MyPokemon pokemon = pokemonRepository
                                    .searchById(id);
                            pokemon.getFood().remove(food);
                            updatePokemon(pokemon, pokemonRepository
                                    .searchById(id)
                                    .getId());
                            if (food.getPokemons().isEmpty()) {
                                foodService.deleteFoodById(food.getId());
                            } else {
                                foodService.updateFood(food, food.getId());
                            }
                        }
                    }
                } catch (Exception e) {
                    throw new ServerException(SERVER_ERROR_MSG);
                }
            }
        try {
            pokemonRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServerException(SERVER_ERROR_MSG);
        }
    }

    public void updatePokemon(final MyPokemon pokemon, final Long id) {
        if (pokemon.getName() == null || pokemon.getName().equals(" ")) {
            throw new BadRequestException(INVALID_INFO_MSG);
        }
        try {
            if (pokemonRepository.findById(id).isPresent()) {
                pokemon.setId(id);
                pokemonRepository.save(pokemon);
                return;
            }

        } catch (Exception e) {
            throw new ServerException(SERVER_ERROR_MSG);
        }
        throw new NotFoundException(NOT_FOUND_MSG);
    }

    public Set<Food> getFood(final Long id) {
        if (!pokemonRepository.findById(id).isPresent()) {
            throw new BadRequestException(INVALID_INFO_MSG);
        }
        try {
            List<Food> food = new ArrayList<>(pokemonRepository
                    .searchById(id).getFood());
            if (!food.isEmpty()) {
                return pokemonRepository.searchById(id).getFood();
            }
        } catch (Exception e) {
            throw new ServerException(SERVER_ERROR_MSG);
        }
        throw new NotFoundException(NOT_FOUND_MSG);
    }

    public List<MyPokemon> getALL() {
        try {
            return pokemonRepository.findAll().stream()
                    .sorted((pok1, pok2) -> pok1.getId().compareTo(pok2.getId())).toList();
        } catch (Exception e) {
            throw new ServerException(SERVER_ERROR_MSG);
        }
    }
}
