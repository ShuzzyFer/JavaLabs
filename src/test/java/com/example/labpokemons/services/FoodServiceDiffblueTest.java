package com.example.labpokemons.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.labpokemons.cache.EntityCache;
import com.example.labpokemons.exceptions.BadRequestException;
import com.example.labpokemons.exceptions.NotFoundException;
import com.example.labpokemons.exceptions.ServerException;
import com.example.labpokemons.models.Food;
import com.example.labpokemons.models.MyPokemon;
import com.example.labpokemons.repositories.FoodRepository;
import com.example.labpokemons.repositories.PokemonRepository;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {FoodService.class})
@ExtendWith(SpringExtension.class)
class FoodServiceDiffblueTest {
    @MockBean
    private EntityCache<String, Food> entityCache;

    @MockBean
    private FoodRepository foodRepository;

    @Autowired
    private FoodService foodService;

    @MockBean
    private PokemonRepository pokemonRepository;

    /**
     * Method under test: {@link FoodService#searchByName(String)}
     */
    @Test
    void testSearchByName() {
        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Name");
        food.setPokemons(new HashSet<>());
        when(entityCache.get(Mockito.<String>any())).thenReturn(food);
        Food actualSearchByNameResult = foodService.searchByName("Name");
        verify(entityCache).get(Mockito.<String>any());
        assertSame(food, actualSearchByNameResult);
    }

    /**
     * Method under test: {@link FoodService#searchByName(String)}
     */
    @Test
    void testSearchByName2() {
        when(entityCache.get(Mockito.<String>any())).thenThrow(new NotFoundException("An error occurred"));
        assertThrows(ServerException.class, () -> foodService.searchByName("Name"));
        verify(entityCache).get(Mockito.<String>any());
    }

    /**
     * Method under test: {@link FoodService#searchByName(String)}
     */
    @Test
    void testSearchByName3() {
        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName(null);
        food.setPokemons(new HashSet<>());
        when(entityCache.get(Mockito.<String>any())).thenReturn(food);
        assertThrows(ServerException.class, () -> foodService.searchByName("Name"));
        verify(entityCache).get(Mockito.<String>any());
    }

    /**
     * Method under test: {@link FoodService#searchByName(String)}
     */
    @Test
    void testSearchByName4() {
        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("");
        food.setPokemons(new HashSet<>());
        when(entityCache.get(Mockito.<String>any())).thenReturn(food);
        assertThrows(NotFoundException.class, () -> foodService.searchByName("Name"));
        verify(entityCache).get(Mockito.<String>any());
    }

    /**
     * Method under test: {@link FoodService#searchByName(String)}
     */
    @Test
    void testSearchByName5() {
        assertThrows(BadRequestException.class, () -> foodService.searchByName(" "));
    }

    /**
     * Method under test: {@link FoodService#searchByName(String)}
     */
    @Test
    void testSearchByName6() {
        assertThrows(BadRequestException.class, () -> foodService.searchByName(null));
    }

    /**
     * Method under test: {@link FoodService#insertFood(Food, List)}
     */
    @Test
    void testInsertFood() {
        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Name");
        food.setPokemons(new HashSet<>());
        when(foodRepository.searchByName(Mockito.<String>any())).thenReturn(food);

        Food food2 = new Food();
        food2.setDescription("The characteristics of someone or something");
        food2.setId(1L);
        food2.setName("Name");
        food2.setPokemons(new HashSet<>());
        foodService.insertFood(food2, new ArrayList<>());
        verify(foodRepository).searchByName(Mockito.<String>any());
        assertEquals("Name", food2.getName());
        assertEquals("The characteristics of someone or something", food2.getDescription());
        assertEquals(1L, food2.getId().longValue());
        assertTrue(food2.getPokemons().isEmpty());
    }

    /**
     * Method under test: {@link FoodService#insertFood(Food, List)}
     */
    @Test
    void testInsertFood2() {
        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Name");
        food.setPokemons(new HashSet<>());
        when(foodRepository.searchByName(Mockito.<String>any())).thenReturn(food);

        Food food2 = new Food();
        food2.setDescription("The characteristics of someone or something");
        food2.setId(1L);
        food2.setName("Name");
        food2.setPokemons(new HashSet<>());

        ArrayList<Long> ids = new ArrayList<>();
        ids.add(1L);
        foodService.insertFood(food2, ids);
        verify(foodRepository).searchByName(Mockito.<String>any());
        assertEquals("Name", food2.getName());
        assertEquals("The characteristics of someone or something", food2.getDescription());
        assertEquals(1L, food2.getId().longValue());
        assertTrue(food2.getPokemons().isEmpty());
    }

    @Test
    void testInsertFood3() {
        Food food = new Food();
        food.setName("Apple");
        Long id = 1L;
        MyPokemon pokemon = new MyPokemon();
        pokemon.setId(id);

        when(foodRepository.searchByName(food.getName())).thenReturn(null);
        when(pokemonRepository.findById(id)).thenReturn(Optional.of(pokemon));
        when(pokemonRepository.searchById(id)).thenReturn(pokemon);

        foodService.insertFood(food, Arrays.asList(id));

        verify(entityCache).put(food.getName(), food);
        verify(foodRepository).save(food);
        assertFalse(food.getPokemons().isEmpty());
    }

    @Test
    void testInsertFoodWithEmptyPokemons() {
        Food food = new Food();
        food.setName("Apple");
        Long id = 1L;

        when(foodRepository.searchByName(food.getName())).thenReturn(null);
        when(pokemonRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            foodService.insertFood(food, List.of(id));
        });
    }

    /**
     * Method under test: {@link FoodService#insertFood(Food, List)}
     */
    @Test
    void testInsertFood5() {
        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Name");
        food.setPokemons(new HashSet<>());
        when(foodRepository.searchByName(Mockito.<String>any())).thenReturn(food);

        Food food2 = new Food();
        food2.setDescription("The characteristics of someone or something");
        food2.setId(1L);
        food2.setName("Name");
        food2.setPokemons(new HashSet<>());

        ArrayList<Long> ids = new ArrayList<>();
        ids.add(0L);
        ids.add(1L);
        foodService.insertFood(food2, ids);
        verify(foodRepository).searchByName(Mockito.<String>any());
        assertEquals("Name", food2.getName());
        assertEquals("The characteristics of someone or something", food2.getDescription());
        assertEquals(1L, food2.getId().longValue());
        assertTrue(food2.getPokemons().isEmpty());
    }

    /**
     * Method under test: {@link FoodService#insertFood(Food, List)}
     */
    @Test
    void testInsertFood4() {
        when(foodRepository.searchByName(Mockito.<String>any())).thenThrow(new NotFoundException("An error occurred"));

        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Name");
        food.setPokemons(new HashSet<>());
        assertThrows(ServerException.class, () -> foodService.insertFood(food, new ArrayList<>()));
        verify(foodRepository).searchByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link FoodService#deleteFoodById(Long)}
     */
    @Test
    void testDeleteFoodById() {
        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Name");
        food.setPokemons(new HashSet<>());
        Optional<Food> ofResult = Optional.of(food);
        doNothing().when(foodRepository).deleteById(Mockito.<Long>any());
        when(foodRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        doNothing().when(entityCache).remove(Mockito.<String>any());
        foodService.deleteFoodById(1L);
        verify(entityCache).remove(Mockito.<String>any());
        verify(foodRepository).deleteById(Mockito.<Long>any());
        verify(foodRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link FoodService#deleteFoodById(Long)}
     */
    @Test
    void testDeleteFoodById2() {
        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Name");
        food.setPokemons(new HashSet<>());
        Optional<Food> ofResult = Optional.of(food);
        when(foodRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        doThrow(new BadRequestException("An error occurred")).when(entityCache).remove(Mockito.<String>any());
        assertThrows(BadRequestException.class, () -> foodService.deleteFoodById(1L));
        verify(entityCache).remove(Mockito.<String>any());
        verify(foodRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link FoodService#deleteFoodById(Long)}
     */
    @Test
    void testDeleteFoodById3() {
        Optional<Food> emptyResult = Optional.empty();
        when(foodRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(BadRequestException.class, () -> foodService.deleteFoodById(1L));
        verify(foodRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link FoodService#updateFood(Food, Long)}
     */
    @Test
    void testUpdateFood() {
        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Name");
        food.setPokemons(new HashSet<>());
        Optional<Food> ofResult = Optional.of(food);

        Food food2 = new Food();
        food2.setDescription("The characteristics of someone or something");
        food2.setId(1L);
        food2.setName("Name");
        food2.setPokemons(new HashSet<>());
        when(foodRepository.save(Mockito.<Food>any())).thenReturn(food2);
        when(foodRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        doNothing().when(entityCache).put(Mockito.<String>any(), Mockito.<Food>any());
        doNothing().when(entityCache).remove(Mockito.<String>any());

        Food food3 = new Food();
        food3.setDescription("The characteristics of someone or something");
        food3.setId(1L);
        food3.setName("Name");
        food3.setPokemons(new HashSet<>());
        foodService.updateFood(food3, 1L);
        verify(entityCache).put(Mockito.<String>any(), Mockito.<Food>any());
        verify(entityCache).remove(Mockito.<String>any());
        verify(foodRepository, atLeast(1)).findById(Mockito.<Long>any());
        verify(foodRepository).save(Mockito.<Food>any());
        assertEquals(1L, food3.getId().longValue());
    }

    /**
     * Method under test: {@link FoodService#updateFood(Food, Long)}
     */
    @Test
    void testUpdateFood2() {
        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Name");
        food.setPokemons(new HashSet<>());
        Optional<Food> ofResult = Optional.of(food);
        when(foodRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        doThrow(new NotFoundException("An error occurred")).when(entityCache).remove(Mockito.<String>any());

        Food food2 = new Food();
        food2.setDescription("The characteristics of someone or something");
        food2.setId(1L);
        food2.setName("Name");
        food2.setPokemons(new HashSet<>());
        assertThrows(ServerException.class, () -> foodService.updateFood(food2, 1L));
        verify(entityCache).remove(Mockito.<String>any());
        verify(foodRepository, atLeast(1)).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link FoodService#updateFood(Food, Long)}
     */
    @Test
    void testUpdateFood3() {
        Optional<Food> emptyResult = Optional.empty();
        when(foodRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Name");
        food.setPokemons(new HashSet<>());
        assertThrows(NotFoundException.class, () -> foodService.updateFood(food, 1L));
        verify(foodRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link FoodService#getPokemons(Long)}
     */
    @Test
    void testGetPokemons() {
        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Name");
        HashSet<MyPokemon> pokemons = new HashSet<>();
        food.setPokemons(pokemons);
        Optional<Food> ofResult = Optional.of(food);
        when(foodRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        Set<MyPokemon> actualPokemons = foodService.getPokemons(1L);
        verify(foodRepository).findById(Mockito.<Long>any());
        assertTrue(actualPokemons.isEmpty());
        assertSame(pokemons, actualPokemons);
    }

    /**
     * Method under test: {@link FoodService#getPokemons(Long)}
     */
    @Test
    void testGetPokemons2() {
        Optional<Food> emptyResult = Optional.empty();
        when(foodRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(NotFoundException.class, () -> foodService.getPokemons(1L));
        verify(foodRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link FoodService#getPokemons(Long)}
     */
    @Test
    void testGetPokemons3() {
        when(foodRepository.findById(Mockito.<Long>any())).thenThrow(new BadRequestException("An error occurred"));
        assertThrows(BadRequestException.class, () -> foodService.getPokemons(1L));
        verify(foodRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link FoodService#getALL()}
     */
    @Test
    void testGetALL() {
        when(foodRepository.findAll()).thenReturn(new ArrayList<>());
        List<Food> actualALL = foodService.getALL();
        verify(foodRepository).findAll();
        assertTrue(actualALL.isEmpty());
    }

    /**
     * Method under test: {@link FoodService#getALL()}
     */
    @Test
    void testGetALL2() {
        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Name");
        food.setPokemons(new HashSet<>());

        ArrayList<Food> foodList = new ArrayList<>();
        foodList.add(food);
        when(foodRepository.findAll()).thenReturn(foodList);
        doNothing().when(entityCache).put(Mockito.<String>any(), Mockito.<Food>any());
        List<Food> actualALL = foodService.getALL();
        verify(entityCache).put(Mockito.<String>any(), Mockito.<Food>any());
        verify(foodRepository, atLeast(1)).findAll();
        assertEquals(1, actualALL.size());
    }

    /**
     * Method under test: {@link FoodService#getALL()}
     */
    @Test
    void testGetALL3() {
        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Name");
        food.setPokemons(new HashSet<>());

        ArrayList<Food> foodList = new ArrayList<>();
        foodList.add(food);
        when(foodRepository.findAll()).thenReturn(foodList);
        doThrow(new BadRequestException("An error occurred")).when(entityCache)
                .put(Mockito.<String>any(), Mockito.<Food>any());
        assertThrows(ServerException.class, () -> foodService.getALL());
        verify(entityCache).put(Mockito.<String>any(), Mockito.<Food>any());
        verify(foodRepository, atLeast(1)).findAll();
    }
}
