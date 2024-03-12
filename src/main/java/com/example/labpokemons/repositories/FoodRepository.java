package com.example.labpokemons.repositories;

import com.example.labpokemons.models.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    @Query("SELECT food FROM Food food WHERE food.name=?1")
    Food searchByName(String name);
}
