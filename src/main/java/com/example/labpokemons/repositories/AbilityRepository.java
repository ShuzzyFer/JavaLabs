package com.example.labpokemons.repositories;

import com.example.labpokemons.models.Ability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbilityRepository extends JpaRepository<Ability, Long> {
}
