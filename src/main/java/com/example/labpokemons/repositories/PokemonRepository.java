package com.example.labpokemons.repositories;

import com.example.labpokemons.models.MyPokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface    PokemonRepository extends JpaRepository<MyPokemon, Long> {
    @Query("SELECT myPokemon FROM MyPokemon myPokemon WHERE myPokemon.name=?1")
    List<MyPokemon> searchByName(String name);

    @Query("SELECT myPokemon FROM MyPokemon myPokemon WHERE myPokemon.id=?1")
    MyPokemon searchById(Long name);

}
