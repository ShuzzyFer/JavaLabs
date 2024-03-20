package com.example.labpokemons.services;

import com.example.labpokemons.models.Ability;
import com.example.labpokemons.repositories.AbilityRepository;
import com.example.labpokemons.repositories.PokemonRepository;
import com.github.oscar0812.pokeapi.models.pokemon.Pokemon;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbilityService {
    private final AbilityRepository abilityRepository;
    private final PokemonRepository pokemonRepository;

    public AbilityService(AbilityRepository abilityRepository, PokemonRepository pokemonRepository) {
        this.abilityRepository = abilityRepository;
        this.pokemonRepository = pokemonRepository;
    }

    public Ability parseAbility(Pokemon pokemon, int i) {
        Ability ability = new Ability();
        ability.setDescription(pokemon.getAbilities().get(i).getAbility().getFlavorTextEntries().get(0).getFlavorText());
        ability.setName(pokemon.getAbilities().get(i).getAbility().getName());
        return ability;
    }

    public List<Ability> searchByName(String name) {
        return abilityRepository.searchByName(name);
    }

    public void insertAbility(Ability ability, Long id) {
        ability.setPokemon(pokemonRepository.searchById(id));
        abilityRepository.save(ability);
    }

    public void deleteAbilityById(Long id) {
        abilityRepository.deleteById(id);
    }

    public void updateAbility(Ability ability, Long id) {
        ability.setId(id);
        abilityRepository.save(ability);
    }
    public List<Ability> searchByPokemonName(String name) {
        return abilityRepository.searchAbilitiesByPokemon(name);
    }
}
