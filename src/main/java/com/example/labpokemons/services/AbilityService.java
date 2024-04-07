package com.example.labpokemons.services;

import com.example.labpokemons.exceptions.BadRequestException;
import com.example.labpokemons.exceptions.NotFoundException;
import com.example.labpokemons.exceptions.ServerException;
import com.example.labpokemons.models.Ability;
import com.example.labpokemons.repositories.AbilityRepository;
import com.example.labpokemons.repositories.PokemonRepository;
import com.github.oscar0812.pokeapi.models.pokemon.Pokemon;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.labpokemons.utilities.Constants.*;

@Service
public class AbilityService {
    private final AbilityRepository abilityRepository;
    private final PokemonRepository pokemonRepository;

    public AbilityService(final AbilityRepository abilityRepository,
                          final PokemonRepository pokemonRepository) {
        this.abilityRepository = abilityRepository;
        this.pokemonRepository = pokemonRepository;
    }

    public Ability parseAbility(final Pokemon pokemon, final int i) {
        Ability ability = new Ability();
        ability.setDescription(pokemon.getAbilities().get(i)
                .getAbility().getFlavorTextEntries()
                .get(0)
                .getFlavorText());
        ability.setName(pokemon.getAbilities().get(i).getAbility().getName());
        return ability;
    }

    public List<Ability> searchByName(final String name) {
        if (name == null || name.equals(" ")) {
            throw new BadRequestException(INVALID_INFO_MSG);
        } else {
            try {
                List<Ability> result = new ArrayList<>(abilityRepository
                        .searchByName(name));
                if (!result.isEmpty()) {
                    return result;
                }
            } catch (Exception e) {
                throw new ServerException(SERVER_ERROR_MSG);
            }
        }
        throw new NotFoundException(NOT_FOUND_MSG);
    }

    public void insertAbility(final Ability ability, final Long id) {
        if (ability.getName() == null || ability.getName().equals(" ")) {
            throw new BadRequestException(INVALID_INFO_MSG);
        }
        try {
            if (ability.getName() != null) {
                ability.setPokemon(pokemonRepository.searchById(id));
                abilityRepository.save(ability);
                return;
            }
        } catch (Exception e) {
            throw new ServerException(SERVER_ERROR_MSG);
        }
        throw new NotFoundException(NOT_FOUND_MSG);
    }

    public void deleteAbilityById(final Long id) {
        if (!abilityRepository.findById(id).isPresent()) {
            throw new BadRequestException(INVALID_INFO_MSG);
        } else {
            try {
                abilityRepository.deleteById(id);
            } catch (Exception e) {
                throw new ServerException(SERVER_ERROR_MSG);
            }
        }

    }

    public void updateAbility(final Ability ability, final Long id) {
        if (!abilityRepository.findById(id).isPresent()) {
            throw new BadRequestException(INVALID_INFO_MSG);
        } else {
            try {
                ability.setId(id);
                abilityRepository.save(ability);
            } catch (Exception e) {
                throw new ServerException(SERVER_ERROR_MSG);
            }
        }
    }

    public List<Ability> searchByPokemonName(final String name) {
        if (name == null || name.equals(" ")) {
            throw new BadRequestException(INVALID_INFO_MSG);
        } else {
            try {
                return abilityRepository.searchAbilitiesByPokemon(name);
            } catch (Exception e) {
                throw new ServerException(SERVER_ERROR_MSG);
            }
        }
    }
}
