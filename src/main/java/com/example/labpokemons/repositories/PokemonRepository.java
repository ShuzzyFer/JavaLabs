package com.example.labpokemons.repositories;

import com.example.labpokemons.models.MyPokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<MyPokemon, Long> {
}
