package com.example.labpokemons;

import com.example.labpokemons.controllers.PokeController;
import com.example.labpokemons.models.MyPokemon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PokeControllerTest {
@Autowired
private PokeController pokeController;
    @Test
    void testGetPokemonsList() throws IOException {
        List<MyPokemon> pokemons = pokeController.getPokemons();
        // Assert that the list is not empty
        assertFalse(pokemons.isEmpty());
        // Assert that each PokemonData object has a name and url
        for (MyPokemon pokemon : pokemons) {
            assertNotNull(pokemon.getName());
            assertNotNull(pokemon.getUrl());
        }
    }

    @Test
    void testGetPokemonByName() throws IOException {
        List<MyPokemon> pokemonList = pokeController.getPokemons();
        assertEquals(20, pokemonList.size());
    }
}
