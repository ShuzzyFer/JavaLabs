package com.example.labpokemons.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.labpokemons.exceptions.BadRequestException;
import com.example.labpokemons.exceptions.NotFoundException;
import com.example.labpokemons.exceptions.ServerException;
import com.example.labpokemons.models.Ability;
import com.example.labpokemons.models.Food;
import com.example.labpokemons.models.MyPokemon;
import com.example.labpokemons.repositories.FoodRepository;
import com.example.labpokemons.repositories.PokemonRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PokemonService.class})
@ExtendWith(SpringExtension.class)
class PokemonServiceTest {
    @MockBean
    private AbilityService abilityService;

    @MockBean
    private FoodRepository foodRepository;

    @MockBean
    private FoodService foodService;

    @MockBean
    private PokemonRepository pokemonRepository;

    @Autowired
    private PokemonService pokemonService;

    @Test
    void testSearchByName_found() {
        MyPokemon pikachu = new MyPokemon();
        pikachu.setName("Pikachu");
        when(pokemonRepository.findAll()).thenReturn(List.of(pikachu));

        List<MyPokemon> result = pokemonService.searchByName("Pikachu");
        assertEquals(1, result.size());
        assertEquals(pikachu, result.get(0));
    }

    @Test
    void testSearchByName_notFound() {
        when(pokemonRepository.findAll()).thenReturn(List.of());

        assertThrows(NotFoundException.class, () -> pokemonService.searchByName("Pikachu"));
    }

    @Test
    void testSearchByName_nullName() {
        assertThrows(BadRequestException.class, () -> pokemonService.searchByName(null));
    }

    @Test
    void testSearchByName_emptyName() {
        assertThrows(BadRequestException.class, () -> pokemonService.searchByName(" "));
    }

    @Test
    void testSearchByName_serverError() {
        when(pokemonRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(ServerException.class, () -> pokemonService.searchByName("Pikachu"));
    }
    @Test
    void testInsertPokemon() {
        MyPokemon myPokemon = new MyPokemon();
        ArrayList<Ability> abilities = new ArrayList<>();
        myPokemon.setAbilities(abilities);
        myPokemon.setFood(new HashSet<>());
        myPokemon.setId(1L);
        myPokemon.setName("Name");
        myPokemon.setUrl("https://example.org/example");
        when(pokemonRepository.save(Mockito.<MyPokemon>any())).thenReturn(myPokemon);

        MyPokemon pokemon = new MyPokemon();
        pokemon.setAbilities(new ArrayList<>());
        pokemon.setFood(new HashSet<>());
        pokemon.setId(1L);
        pokemon.setName("Name");
        pokemon.setUrl("https://example.org/example");
        pokemonService.insertPokemon(pokemon);
        verify(pokemonRepository).save(Mockito.<MyPokemon>any());
        assertEquals("Name", pokemon.getName());
        assertEquals("https://example.org/example", pokemon.getUrl());
        assertEquals(1L, pokemon.getId().longValue());
        assertTrue(pokemon.getFood().isEmpty());
        assertEquals(abilities, pokemon.getAbilities());
    }

    /**
     * Method under test: {@link PokemonService#insertPokemon(MyPokemon)}
     */
    @Test
    void testInsertPokemon2() {
        MyPokemon pokemon = new MyPokemon();
        pokemon.setAbilities(new ArrayList<>());
        pokemon.setFood(new HashSet<>());
        pokemon.setId(null);
        pokemon.setName("Name");
        pokemon.setUrl("https://example.org/example");
        assertThrows(NotFoundException.class, () -> pokemonService.insertPokemon(pokemon));
    }

    /**
     * Method under test: {@link PokemonService#insertPokemon(MyPokemon)}
     */
    @Test
    void testInsertPokemon3() {
        MyPokemon pokemon = new MyPokemon();
        pokemon.setAbilities(new ArrayList<>());
        pokemon.setFood(new HashSet<>());
        pokemon.setId(1L);
        pokemon.setName(" ");
        pokemon.setUrl("https://example.org/example");
        assertThrows(BadRequestException.class, () -> pokemonService.insertPokemon(pokemon));
    }

    /**
     * Method under test: {@link PokemonService#insertPokemon(MyPokemon)}
     */
    @Test
    void testInsertPokemon4() {
        MyPokemon pokemon = new MyPokemon();
        pokemon.setAbilities(new ArrayList<>());
        pokemon.setFood(new HashSet<>());
        pokemon.setId(1L);
        pokemon.setName(null);
        pokemon.setUrl("https://example.org/example");
        assertThrows(BadRequestException.class, () -> pokemonService.insertPokemon(pokemon));
    }

    /**
     * Method under test: {@link PokemonService#insertPokemon(MyPokemon)}
     */
    @Test
    void testInsertPokemon5() {
        when(pokemonRepository.save(Mockito.<MyPokemon>any())).thenThrow(new BadRequestException("An error occurred"));

        MyPokemon pokemon = new MyPokemon();
        pokemon.setAbilities(new ArrayList<>());
        pokemon.setFood(new HashSet<>());
        pokemon.setId(1L);
        pokemon.setName("Name");
        pokemon.setUrl("https://example.org/example");
        assertThrows(ServerException.class, () -> pokemonService.insertPokemon(pokemon));
        verify(pokemonRepository).save(Mockito.<MyPokemon>any());
    }

    /**
     * Method under test: {@link PokemonService#deletePokemonById(Long)}
     */
    @Test
    void testDeletePokemonById() {
        MyPokemon myPokemon = new MyPokemon();
        myPokemon.setAbilities(new ArrayList<>());
        myPokemon.setFood(new HashSet<>());
        myPokemon.setId(1L);
        myPokemon.setName("Name");
        myPokemon.setUrl("https://example.org/example");
        Optional<MyPokemon> ofResult = Optional.of(myPokemon);

        MyPokemon myPokemon2 = new MyPokemon();
        myPokemon2.setAbilities(new ArrayList<>());
        myPokemon2.setFood(new HashSet<>());
        myPokemon2.setId(1L);
        myPokemon2.setName("Name");
        myPokemon2.setUrl("https://example.org/example");
        doNothing().when(pokemonRepository).deleteById(Mockito.<Long>any());
        when(pokemonRepository.searchById(Mockito.<Long>any())).thenReturn(myPokemon2);
        when(pokemonRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        pokemonService.deletePokemonById(1L);
        verify(pokemonRepository).searchById(Mockito.<Long>any());
        verify(pokemonRepository).deleteById(Mockito.<Long>any());
        verify(pokemonRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link PokemonService#deletePokemonById(Long)}
     */
    @Test
    void testDeletePokemonById2() {
        MyPokemon myPokemon = new MyPokemon();
        myPokemon.setAbilities(new ArrayList<>());
        myPokemon.setFood(new HashSet<>());
        myPokemon.setId(1L);
        myPokemon.setName("Name");
        myPokemon.setUrl("https://example.org/example");
        Optional<MyPokemon> ofResult = Optional.of(myPokemon);

        MyPokemon myPokemon2 = new MyPokemon();
        myPokemon2.setAbilities(new ArrayList<>());
        myPokemon2.setFood(new HashSet<>());
        myPokemon2.setId(1L);
        myPokemon2.setName("Name");
        myPokemon2.setUrl("https://example.org/example");
        doThrow(new BadRequestException("An error occurred")).when(pokemonRepository).deleteById(Mockito.<Long>any());
        when(pokemonRepository.searchById(Mockito.<Long>any())).thenReturn(myPokemon2);
        when(pokemonRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(ServerException.class, () -> pokemonService.deletePokemonById(1L));
        verify(pokemonRepository).searchById(Mockito.<Long>any());
        verify(pokemonRepository).deleteById(Mockito.<Long>any());
        verify(pokemonRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link PokemonService#deletePokemonById(Long)}
     */
    @Test
    void testDeletePokemonById3() {
        MyPokemon myPokemon = new MyPokemon();
        myPokemon.setAbilities(new ArrayList<>());
        myPokemon.setFood(new HashSet<>());
        myPokemon.setId(1L);
        myPokemon.setName("Name");
        myPokemon.setUrl("https://example.org/example");
        Optional<MyPokemon> ofResult = Optional.of(myPokemon);

        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Name");
        food.setPokemons(new HashSet<>());

        HashSet<Food> food2 = new HashSet<>();
        food2.add(food);

        MyPokemon myPokemon2 = new MyPokemon();
        myPokemon2.setAbilities(new ArrayList<>());
        myPokemon2.setFood(food2);
        myPokemon2.setId(1L);
        myPokemon2.setName("Name");
        myPokemon2.setUrl("https://example.org/example");

        MyPokemon myPokemon3 = new MyPokemon();
        myPokemon3.setAbilities(new ArrayList<>());
        myPokemon3.setFood(new HashSet<>());
        myPokemon3.setId(1L);
        myPokemon3.setName("Name");
        myPokemon3.setUrl("https://example.org/example");
        when(pokemonRepository.save(Mockito.<MyPokemon>any())).thenReturn(myPokemon3);
        doNothing().when(pokemonRepository).deleteById(Mockito.<Long>any());
        when(pokemonRepository.searchById(Mockito.<Long>any())).thenReturn(myPokemon2);
        when(pokemonRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        doNothing().when(foodService).deleteFoodById(Mockito.<Long>any());
        pokemonService.deletePokemonById(1L);
        verify(pokemonRepository, atLeast(1)).searchById(Mockito.<Long>any());
        verify(foodService).deleteFoodById(Mockito.<Long>any());
        verify(pokemonRepository).deleteById(Mockito.<Long>any());
        verify(pokemonRepository, atLeast(1)).findById(Mockito.<Long>any());
        verify(pokemonRepository).save(Mockito.<MyPokemon>any());
    }

    /**
     * Method under test: {@link PokemonService#deletePokemonById(Long)}
     */
    @Test
    void testDeletePokemonById4() {
        MyPokemon myPokemon = new MyPokemon();
        myPokemon.setAbilities(new ArrayList<>());
        myPokemon.setFood(new HashSet<>());
        myPokemon.setId(1L);
        myPokemon.setName("Name");
        myPokemon.setUrl("https://example.org/example");
        Optional<MyPokemon> ofResult = Optional.of(myPokemon);

        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Name");
        food.setPokemons(new HashSet<>());

        HashSet<Food> food2 = new HashSet<>();
        food2.add(food);

        MyPokemon myPokemon2 = new MyPokemon();
        myPokemon2.setAbilities(new ArrayList<>());
        myPokemon2.setFood(food2);
        myPokemon2.setId(1L);
        myPokemon2.setName("Name");
        myPokemon2.setUrl("https://example.org/example");

        MyPokemon myPokemon3 = new MyPokemon();
        myPokemon3.setAbilities(new ArrayList<>());
        myPokemon3.setFood(new HashSet<>());
        myPokemon3.setId(1L);
        myPokemon3.setName("Name");
        myPokemon3.setUrl("https://example.org/example");
        when(pokemonRepository.save(Mockito.<MyPokemon>any())).thenReturn(myPokemon3);
        when(pokemonRepository.searchById(Mockito.<Long>any())).thenReturn(myPokemon2);
        when(pokemonRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        doThrow(new BadRequestException("An error occurred")).when(foodService).deleteFoodById(Mockito.<Long>any());
        assertThrows(ServerException.class, () -> pokemonService.deletePokemonById(1L));
        verify(pokemonRepository, atLeast(1)).searchById(Mockito.<Long>any());
        verify(foodService).deleteFoodById(Mockito.<Long>any());
        verify(pokemonRepository, atLeast(1)).findById(Mockito.<Long>any());
        verify(pokemonRepository).save(Mockito.<MyPokemon>any());
    }

    /**
     * Method under test: {@link PokemonService#deletePokemonById(Long)}
     */
    @Test
    void testDeletePokemonById5() {
        MyPokemon myPokemon = new MyPokemon();
        myPokemon.setAbilities(new ArrayList<>());
        myPokemon.setFood(new HashSet<>());
        myPokemon.setId(1L);
        myPokemon.setName("Name");
        myPokemon.setUrl("https://example.org/example");
        Optional<MyPokemon> ofResult = Optional.of(myPokemon);

        MyPokemon myPokemon2 = new MyPokemon();
        myPokemon2.setAbilities(new ArrayList<>());
        myPokemon2.setFood(new HashSet<>());
        myPokemon2.setId(1L);
        myPokemon2.setName(" ");
        myPokemon2.setUrl("https://example.org/example");

        HashSet<MyPokemon> pokemons = new HashSet<>();
        pokemons.add(myPokemon2);

        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Name");
        food.setPokemons(pokemons);

        HashSet<Food> food2 = new HashSet<>();
        food2.add(food);

        MyPokemon myPokemon3 = new MyPokemon();
        myPokemon3.setAbilities(new ArrayList<>());
        myPokemon3.setFood(food2);
        myPokemon3.setId(1L);
        myPokemon3.setName("Name");
        myPokemon3.setUrl("https://example.org/example");

        MyPokemon myPokemon4 = new MyPokemon();
        myPokemon4.setAbilities(new ArrayList<>());
        myPokemon4.setFood(new HashSet<>());
        myPokemon4.setId(1L);
        myPokemon4.setName("Name");
        myPokemon4.setUrl("https://example.org/example");
        when(pokemonRepository.save(Mockito.<MyPokemon>any())).thenReturn(myPokemon4);
        doNothing().when(pokemonRepository).deleteById(Mockito.<Long>any());
        when(pokemonRepository.searchById(Mockito.<Long>any())).thenReturn(myPokemon3);
        when(pokemonRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        doNothing().when(foodService).updateFood(Mockito.<Food>any(), Mockito.<Long>any());
        pokemonService.deletePokemonById(1L);
        verify(pokemonRepository, atLeast(1)).searchById(Mockito.<Long>any());
        verify(foodService).updateFood(Mockito.<Food>any(), Mockito.<Long>any());
        verify(pokemonRepository).deleteById(Mockito.<Long>any());
        verify(pokemonRepository, atLeast(1)).findById(Mockito.<Long>any());
        verify(pokemonRepository).save(Mockito.<MyPokemon>any());
    }

    /**
     * Method under test: {@link PokemonService#deletePokemonById(Long)}
     */
    @Test
    void testDeletePokemonById6() {
        MyPokemon myPokemon = new MyPokemon();
        myPokemon.setAbilities(new ArrayList<>());
        myPokemon.setFood(new HashSet<>());
        myPokemon.setId(1L);
        myPokemon.setName("Name");
        myPokemon.setUrl("https://example.org/example");
        Optional<MyPokemon> ofResult = Optional.of(myPokemon);

        MyPokemon myPokemon2 = new MyPokemon();
        myPokemon2.setAbilities(new ArrayList<>());
        myPokemon2.setFood(new HashSet<>());
        myPokemon2.setId(1L);
        myPokemon2.setName(" ");
        myPokemon2.setUrl("https://example.org/example");

        HashSet<MyPokemon> pokemons = new HashSet<>();
        pokemons.add(myPokemon2);

        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Name");
        food.setPokemons(pokemons);

        HashSet<Food> food2 = new HashSet<>();
        food2.add(food);

        MyPokemon myPokemon3 = new MyPokemon();
        myPokemon3.setAbilities(new ArrayList<>());
        myPokemon3.setFood(food2);
        myPokemon3.setId(1L);
        myPokemon3.setName(" ");
        myPokemon3.setUrl("https://example.org/example");
        when(pokemonRepository.searchById(Mockito.<Long>any())).thenReturn(myPokemon3);
        when(pokemonRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(ServerException.class, () -> pokemonService.deletePokemonById(1L));
        verify(pokemonRepository, atLeast(1)).searchById(Mockito.<Long>any());
        verify(pokemonRepository, atLeast(1)).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link PokemonService#deletePokemonById(Long)}
     */
    @Test
    void testDeletePokemonById7() {
        MyPokemon myPokemon = new MyPokemon();
        myPokemon.setAbilities(new ArrayList<>());
        myPokemon.setFood(new HashSet<>());
        myPokemon.setId(1L);
        myPokemon.setName("Name");
        myPokemon.setUrl("https://example.org/example");
        Optional<MyPokemon> ofResult = Optional.of(myPokemon);

        MyPokemon myPokemon2 = new MyPokemon();
        myPokemon2.setAbilities(new ArrayList<>());
        myPokemon2.setFood(new HashSet<>());
        myPokemon2.setId(1L);
        myPokemon2.setName(" ");
        myPokemon2.setUrl("https://example.org/example");

        HashSet<MyPokemon> pokemons = new HashSet<>();
        pokemons.add(myPokemon2);

        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Name");
        food.setPokemons(pokemons);

        HashSet<Food> food2 = new HashSet<>();
        food2.add(food);

        MyPokemon myPokemon3 = new MyPokemon();
        myPokemon3.setAbilities(new ArrayList<>());
        myPokemon3.setFood(food2);
        myPokemon3.setId(1L);
        myPokemon3.setName(null);
        myPokemon3.setUrl("https://example.org/example");
        when(pokemonRepository.searchById(Mockito.<Long>any())).thenReturn(myPokemon3);
        when(pokemonRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(ServerException.class, () -> pokemonService.deletePokemonById(1L));
        verify(pokemonRepository, atLeast(1)).searchById(Mockito.<Long>any());
        verify(pokemonRepository, atLeast(1)).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link PokemonService#deletePokemonById(Long)}
     */
    @Test
    void testDeletePokemonById8() {
        Optional<MyPokemon> emptyResult = Optional.empty();
        when(pokemonRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(NotFoundException.class, () -> pokemonService.deletePokemonById(1L));
        verify(pokemonRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link PokemonService#updatePokemon(MyPokemon, Long)}
     */
    @Test
    void testUpdatePokemon() {
        MyPokemon myPokemon = new MyPokemon();
        myPokemon.setAbilities(new ArrayList<>());
        myPokemon.setFood(new HashSet<>());
        myPokemon.setId(1L);
        myPokemon.setName("Name");
        myPokemon.setUrl("https://example.org/example");
        Optional<MyPokemon> ofResult = Optional.of(myPokemon);

        MyPokemon myPokemon2 = new MyPokemon();
        myPokemon2.setAbilities(new ArrayList<>());
        myPokemon2.setFood(new HashSet<>());
        myPokemon2.setId(1L);
        myPokemon2.setName("Name");
        myPokemon2.setUrl("https://example.org/example");
        when(pokemonRepository.save(Mockito.<MyPokemon>any())).thenReturn(myPokemon2);
        when(pokemonRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        MyPokemon pokemon = new MyPokemon();
        pokemon.setAbilities(new ArrayList<>());
        pokemon.setFood(new HashSet<>());
        pokemon.setId(1L);
        pokemon.setName("Name");
        pokemon.setUrl("https://example.org/example");
        pokemonService.updatePokemon(pokemon, 1L);
        verify(pokemonRepository).findById(Mockito.<Long>any());
        verify(pokemonRepository).save(Mockito.<MyPokemon>any());
        assertEquals(1L, pokemon.getId().longValue());
    }

    /**
     * Method under test: {@link PokemonService#updatePokemon(MyPokemon, Long)}
     */
    @Test
    void testUpdatePokemon2() {
        MyPokemon myPokemon = new MyPokemon();
        myPokemon.setAbilities(new ArrayList<>());
        myPokemon.setFood(new HashSet<>());
        myPokemon.setId(1L);
        myPokemon.setName("Name");
        myPokemon.setUrl("https://example.org/example");
        Optional<MyPokemon> ofResult = Optional.of(myPokemon);
        when(pokemonRepository.save(Mockito.<MyPokemon>any())).thenThrow(new BadRequestException("An error occurred"));
        when(pokemonRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        MyPokemon pokemon = new MyPokemon();
        pokemon.setAbilities(new ArrayList<>());
        pokemon.setFood(new HashSet<>());
        pokemon.setId(1L);
        pokemon.setName("Name");
        pokemon.setUrl("https://example.org/example");
        assertThrows(ServerException.class, () -> pokemonService.updatePokemon(pokemon, 1L));
        verify(pokemonRepository).findById(Mockito.<Long>any());
        verify(pokemonRepository).save(Mockito.<MyPokemon>any());
    }

    /**
     * Method under test: {@link PokemonService#updatePokemon(MyPokemon, Long)}
     */
    @Test
    void testUpdatePokemon3() {
        when(pokemonRepository.findById(Mockito.<Long>any())).thenReturn(null);

        MyPokemon pokemon = new MyPokemon();
        pokemon.setAbilities(new ArrayList<>());
        pokemon.setFood(new HashSet<>());
        pokemon.setId(1L);
        pokemon.setName("Name");
        pokemon.setUrl("https://example.org/example");
        assertThrows(ServerException.class, () -> pokemonService.updatePokemon(pokemon, 1L));
        verify(pokemonRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link PokemonService#updatePokemon(MyPokemon, Long)}
     */
    @Test
    void testUpdatePokemon4() {
        Optional<MyPokemon> emptyResult = Optional.empty();
        when(pokemonRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        MyPokemon pokemon = new MyPokemon();
        pokemon.setAbilities(new ArrayList<>());
        pokemon.setFood(new HashSet<>());
        pokemon.setId(1L);
        pokemon.setName("Name");
        pokemon.setUrl("https://example.org/example");
        assertThrows(NotFoundException.class, () -> pokemonService.updatePokemon(pokemon, 1L));
        verify(pokemonRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link PokemonService#updatePokemon(MyPokemon, Long)}
     */
    @Test
    void testUpdatePokemon5() {
        MyPokemon pokemon = new MyPokemon();
        pokemon.setAbilities(new ArrayList<>());
        pokemon.setFood(new HashSet<>());
        pokemon.setId(1L);
        pokemon.setName(" ");
        pokemon.setUrl("https://example.org/example");
        assertThrows(BadRequestException.class, () -> pokemonService.updatePokemon(pokemon, 1L));
    }

    /**
     * Method under test: {@link PokemonService#updatePokemon(MyPokemon, Long)}
     */
    @Test
    void testUpdatePokemon6() {
        MyPokemon pokemon = new MyPokemon();
        pokemon.setAbilities(new ArrayList<>());
        pokemon.setFood(new HashSet<>());
        pokemon.setId(1L);
        pokemon.setName(null);
        pokemon.setUrl("https://example.org/example");
        assertThrows(BadRequestException.class, () -> pokemonService.updatePokemon(pokemon, 1L));
    }

    /**
     * Method under test: {@link PokemonService#getFood(Long)}
     */
    @Test
    void testGetFood() {
        MyPokemon myPokemon = new MyPokemon();
        myPokemon.setAbilities(new ArrayList<>());
        myPokemon.setFood(new HashSet<>());
        myPokemon.setId(1L);
        myPokemon.setName("Name");
        myPokemon.setUrl("https://example.org/example");
        Optional<MyPokemon> ofResult = Optional.of(myPokemon);

        MyPokemon myPokemon2 = new MyPokemon();
        myPokemon2.setAbilities(new ArrayList<>());
        myPokemon2.setFood(new HashSet<>());
        myPokemon2.setId(1L);
        myPokemon2.setName("Name");
        myPokemon2.setUrl("https://example.org/example");
        when(pokemonRepository.searchById(Mockito.<Long>any())).thenReturn(myPokemon2);
        when(pokemonRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(NotFoundException.class, () -> pokemonService.getFood(1L));
        verify(pokemonRepository).searchById(Mockito.<Long>any());
        verify(pokemonRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link PokemonService#getFood(Long)}
     */
    @Test
    void testGetFood2() {
        MyPokemon myPokemon = new MyPokemon();
        myPokemon.setAbilities(new ArrayList<>());
        myPokemon.setFood(new HashSet<>());
        myPokemon.setId(1L);
        myPokemon.setName("Name");
        myPokemon.setUrl("https://example.org/example");
        Optional<MyPokemon> ofResult = Optional.of(myPokemon);
        when(pokemonRepository.searchById(Mockito.<Long>any())).thenThrow(new BadRequestException("An error occurred"));
        when(pokemonRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(ServerException.class, () -> pokemonService.getFood(1L));
        verify(pokemonRepository).searchById(Mockito.<Long>any());
        verify(pokemonRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link PokemonService#getFood(Long)}
     */
    @Test
    void testGetFood3() {
        MyPokemon myPokemon = new MyPokemon();
        myPokemon.setAbilities(new ArrayList<>());
        myPokemon.setFood(new HashSet<>());
        myPokemon.setId(1L);
        myPokemon.setName("Name");
        myPokemon.setUrl("https://example.org/example");
        Optional<MyPokemon> ofResult = Optional.of(myPokemon);

        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Required resources are not found");
        food.setPokemons(new HashSet<>());

        HashSet<Food> food2 = new HashSet<>();
        food2.add(food);

        MyPokemon myPokemon2 = new MyPokemon();
        myPokemon2.setAbilities(new ArrayList<>());
        myPokemon2.setFood(food2);
        myPokemon2.setId(1L);
        myPokemon2.setName("Name");
        myPokemon2.setUrl("https://example.org/example");
        when(pokemonRepository.searchById(Mockito.<Long>any())).thenReturn(myPokemon2);
        when(pokemonRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        Set<Food> actualFood = pokemonService.getFood(1L);
        verify(pokemonRepository, atLeast(1)).searchById(Mockito.<Long>any());
        verify(pokemonRepository).findById(Mockito.<Long>any());
        assertEquals(1, actualFood.size());
        assertSame(food2, actualFood);
    }

    /**
     * Method under test: {@link PokemonService#getFood(Long)}
     */
    @Test
    void testGetFood4() {
        Optional<MyPokemon> emptyResult = Optional.empty();
        when(pokemonRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(BadRequestException.class, () -> pokemonService.getFood(1L));
        verify(pokemonRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link PokemonService#getALL()}
     */
    @Test
    void testGetALL() {
        when(pokemonRepository.findAll()).thenReturn(new ArrayList<>());
        List<MyPokemon> actualALL = pokemonService.getALL();
        verify(pokemonRepository).findAll();
        assertTrue(actualALL.isEmpty());
    }

    /**
     * Method under test: {@link PokemonService#getALL()}
     */
    @Test
    void testGetALL2() {
        MyPokemon myPokemon = new MyPokemon();
        myPokemon.setAbilities(new ArrayList<>());
        myPokemon.setFood(new HashSet<>());
        myPokemon.setId(1L);
        myPokemon.setName("Name");
        myPokemon.setUrl("https://example.org/example");

        ArrayList<MyPokemon> myPokemonList = new ArrayList<>();
        myPokemonList.add(myPokemon);
        when(pokemonRepository.findAll()).thenReturn(myPokemonList);
        List<MyPokemon> actualALL = pokemonService.getALL();
        verify(pokemonRepository, atLeast(1)).findAll();
        assertEquals(1, actualALL.size());
    }

    /**
     * Method under test: {@link PokemonService#getALL()}
     */
    @Test
    void testGetALL3() {
        when(pokemonRepository.findAll()).thenThrow(new BadRequestException("An error occurred"));
        assertThrows(ServerException.class, () -> pokemonService.getALL());
        verify(pokemonRepository).findAll();
    }
}
