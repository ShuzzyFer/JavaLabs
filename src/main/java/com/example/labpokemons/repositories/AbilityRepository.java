package com.example.labpokemons.repositories;

import com.example.labpokemons.models.Ability;
import com.example.labpokemons.models.MyPokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AbilityRepository extends JpaRepository<Ability, Long> {
    @Query("SELECT ability FROM Ability ability WHERE ability.name=?1")
    List<Ability> searchByName(String name);
    @Query("SELECT ability FROM Ability ability WHERE ability.id=?1")
    Ability searchById(Long id);

}
