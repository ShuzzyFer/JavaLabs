package com.example.labpokemons.services;

import com.example.labpokemons.models.Ability;
import com.example.labpokemons.models.MyPokemon;
import com.example.labpokemons.repositories.AbilityRepository;
import com.github.oscar0812.pokeapi.models.pokemon.Pokemon;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbilityService {
    private final AbilityRepository abilityRepository;

    public AbilityService(AbilityRepository abilityRepository) {
        this.abilityRepository = abilityRepository;
    }

    public Ability parseAbility(Pokemon pokemon, int i, int j) {
        Ability ability = new Ability();
        ability.setDescription(pokemon.getAbilities().get(i).getAbility().getFlavorTextEntries().get(0).getFlavorText());
        ability.setName(pokemon.getAbilities().get(i).getAbility().getName());
        //ability.setPokemon(myPokemon);
        int minValue = 1;
        int maxValue = 50000;
        ability.setId((long) (minValue + (int) (Math.random() * (maxValue - minValue + 1))));
        return ability;
    }
    public List<MyPokemon> searchByName(String name) {
        return abilityRepository.searchByName(name);
    }
    public void insertAbility(Ability ability) {
        abilityRepository.save(ability);
    }
    public void deleteAbilityById(Long id) {
        abilityRepository.deleteById(id);
    }
    public void updateAbility(Ability ability, Long id) {
        ability.setId(id);
        abilityRepository.save(ability);
    }
}
