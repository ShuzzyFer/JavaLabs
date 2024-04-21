package com.example.labpokemons.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.labpokemons.models.Ability;
import com.example.labpokemons.models.MyPokemon;
import com.example.labpokemons.services.AbilityService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashSet;

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

@ContextConfiguration(classes = {AbilityController.class})
@ExtendWith(SpringExtension.class)
class AbilityControllerTest {
    @Autowired
    private AbilityController abilityController;

    @MockBean
    private AbilityService abilityService;

    /**
     * Method under test: {@link AbilityController#deleteAbility(Long)}
     */
    @Test
    void testDeleteAbility() throws Exception {
        doNothing().when(abilityService).deleteAbilityById(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/deleteAbility/{id}", 1L);
        MockMvcBuilders.standaloneSetup(abilityController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link AbilityController#insertAbility(Ability, Long)}
     */
    @Test
    void testInsertAbility() throws Exception {
        doNothing().when(abilityService).insertAbility(Mockito.<Ability>any(), Mockito.<Long>any());

        MyPokemon pokemon = new MyPokemon();
        pokemon.setAbilities(new ArrayList<>());
        pokemon.setFood(new HashSet<>());
        pokemon.setId(1L);
        pokemon.setName("Name");
        pokemon.setUrl("https://example.org/example");

        Ability ability = new Ability();
        ability.setDescription("The characteristics of someone or something");
        ability.setId(1L);
        ability.setName("Name");
        ability.setPokemon(pokemon);
        String content = (new ObjectMapper()).writeValueAsString(ability);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/postAbility/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(abilityController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link AbilityController#updateAbility(Ability, Long)}
     */
    @Test
    void testUpdateAbility() throws Exception {
        doNothing().when(abilityService).updateAbility(Mockito.<Ability>any(), Mockito.<Long>any());

        MyPokemon pokemon = new MyPokemon();
        pokemon.setAbilities(new ArrayList<>());
        pokemon.setFood(new HashSet<>());
        pokemon.setId(1L);
        pokemon.setName("Name");
        pokemon.setUrl("https://example.org/example");

        Ability ability = new Ability();
        ability.setDescription("The characteristics of someone or something");
        ability.setId(1L);
        ability.setName("Name");
        ability.setPokemon(pokemon);
        String content = (new ObjectMapper()).writeValueAsString(ability);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/updateAbility/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(abilityController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link AbilityController#deleteAbility(Long)}
     */
    @Test
    void testDeleteAbility2() throws Exception {
        doNothing().when(abilityService).deleteAbilityById(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/deleteAbility/{id}", 1L);
        requestBuilder.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(abilityController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link AbilityController#getAbilities(String)}
     */
    @Test
    void testGetAbilities() throws Exception {
        when(abilityService.searchByPokemonName(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getPokemonAbilities/{name}", "Name");
        MockMvcBuilders.standaloneSetup(abilityController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link AbilityController#getAbility(String)}
     */
    @Test
    void testGetAbility() throws Exception {
        when(abilityService.searchByName(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getAbility/{name}", "Name");
        MockMvcBuilders.standaloneSetup(abilityController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
