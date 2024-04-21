package com.example.labpokemons.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.labpokemons.cache.EntityCache;
import com.example.labpokemons.models.Food;
import com.example.labpokemons.repositories.FoodRepository;
import com.example.labpokemons.repositories.PokemonRepository;
import com.example.labpokemons.services.FoodService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {FoodController.class})
@ExtendWith(SpringExtension.class)
class FoodControllerTest {
    @Autowired
    private FoodController foodController;

    @MockBean
    private FoodService foodService;

    /**
     * Method under test: {@link FoodController#insertAbility(Food, List)}
     */
    @Test
    void testInsertAbility() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Name");
        food.setPokemons(new HashSet<>());
        FoodRepository foodRep = mock(FoodRepository.class);
        when(foodRep.searchByName(Mockito.<String>any())).thenReturn(food);
        PokemonRepository pokemonRep = mock(PokemonRepository.class);
        FoodController foodController = new FoodController(new FoodService(foodRep, pokemonRep, new EntityCache<>()));

        Food food2 = new Food();
        food2.setDescription("The characteristics of someone or something");
        food2.setId(1L);
        food2.setName("Name");
        food2.setPokemons(new HashSet<>());
        foodController.insertAbility(food2, new ArrayList<>());
        verify(foodRep).searchByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link FoodController#insertAbility(Food, List)}
     */
    @Test
    void testInsertAbility2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Name");
        food.setPokemons(new HashSet<>());
        FoodRepository foodRep = mock(FoodRepository.class);
        when(foodRep.searchByName(Mockito.<String>any())).thenReturn(food);
        PokemonRepository pokemonRep = mock(PokemonRepository.class);
        FoodController foodController = new FoodController(new FoodService(foodRep, pokemonRep, new EntityCache<>()));

        Food food2 = new Food();
        food2.setDescription("The characteristics of someone or something");
        food2.setId(1L);
        food2.setName("Name");
        food2.setPokemons(new HashSet<>());

        ArrayList<Long> id = new ArrayList<>();
        id.add(1L);
        foodController.insertAbility(food2, id);
        verify(foodRep).searchByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link FoodController#insertAbility(Food, List)}
     */
    @Test
    void testInsertAbility3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Name");
        food.setPokemons(new HashSet<>());
        FoodRepository foodRep = mock(FoodRepository.class);
        when(foodRep.searchByName(Mockito.<String>any())).thenReturn(food);
        PokemonRepository pokemonRep = mock(PokemonRepository.class);
        FoodController foodController = new FoodController(new FoodService(foodRep, pokemonRep, new EntityCache<>()));

        Food food2 = new Food();
        food2.setDescription("The characteristics of someone or something");
        food2.setId(1L);
        food2.setName("Name");
        food2.setPokemons(new HashSet<>());

        ArrayList<Long> id = new ArrayList<>();
        id.add(0L);
        id.add(1L);
        foodController.insertAbility(food2, id);
        verify(foodRep).searchByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link FoodController#getPokemons(Long)}
     */
    @Test
    void testGetPokemons() throws Exception {
        when(foodService.getPokemons(Mockito.<Long>any())).thenReturn(new HashSet<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getFoodPokemons/{id}", 1L);
        MockMvcBuilders.standaloneSetup(foodController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link FoodController#deleteAbility(Long)}
     */
    @Test
    void testDeleteAbility() throws Exception {
        doNothing().when(foodService).deleteFoodById(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/deleteFood/{id}", 1L);
        MockMvcBuilders.standaloneSetup(foodController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link FoodController#getALL()}
     */
    @Test
    void testGetALL() throws Exception {
        when(foodService.getALL()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getAllFood");
        MockMvcBuilders.standaloneSetup(foodController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link FoodController#deleteAbility(Long)}
     */
    @Test
    void testDeleteAbility2() throws Exception {
        doNothing().when(foodService).deleteFoodById(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/deleteFood/{id}", 1L);
        requestBuilder.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(foodController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link FoodController#getFood(String)}
     */
    @Test
    void testGetFood() throws Exception {
        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Name");
        food.setPokemons(new HashSet<>());
        when(foodService.searchByName(Mockito.<String>any())).thenReturn(food);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getFood/{name}", "Name");
        MockMvcBuilders.standaloneSetup(foodController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"name\":\"Name\",\"description\":\"The characteristics of someone or something\"}"));
    }

    /**
     * Method under test: {@link FoodController#updateAbility(Food, Long)}
     */
    @Test
    void testUpdateAbility() throws Exception {
        doNothing().when(foodService).updateFood(Mockito.<Food>any(), Mockito.<Long>any());

        Food food = new Food();
        food.setDescription("The characteristics of someone or something");
        food.setId(1L);
        food.setName("Name");
        food.setPokemons(new HashSet<>());
        String content = (new ObjectMapper()).writeValueAsString(food);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/updateFood/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(foodController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
