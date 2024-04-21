package com.example.labpokemons.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.labpokemons.exceptions.BadRequestException;
import com.example.labpokemons.exceptions.NotFoundException;
import com.example.labpokemons.exceptions.ServerException;
import com.example.labpokemons.models.Ability;
import com.example.labpokemons.models.MyPokemon;
import com.example.labpokemons.repositories.AbilityRepository;
import com.example.labpokemons.repositories.PokemonRepository;
import com.github.oscar0812.pokeapi.models.pokemon.Pokemon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AbilityService.class})
@ExtendWith(SpringExtension.class)
class AbilityServiceDiffblueTest {
    @MockBean
    private AbilityRepository abilityRepository;

    @Autowired
    private AbilityService abilityService;

    @MockBean
    private PokemonRepository pokemonRepository;

    /**
     * Method under test: {@link AbilityService#parseAbility(Pokemon, int)}
     */
    @Test
    void testParseAbility() {
        AbilityService abilityService = new AbilityService(mock(AbilityRepository.class), mock(PokemonRepository.class));
        Pokemon pokemon = mock(Pokemon.class);
        when(pokemon.getAbilities()).thenThrow(new BadRequestException("An error occurred"));
        assertThrows(BadRequestException.class, () -> abilityService.parseAbility(pokemon, 1));
        verify(pokemon).getAbilities();
    }

    /**
     * Method under test: {@link AbilityService#searchByName(String)}
     */
    @Test
    void testSearchByName() {
        when(abilityRepository.searchByName(Mockito.<String>any())).thenReturn(new ArrayList<>());
        assertThrows(NotFoundException.class, () -> abilityService.searchByName("Name"));
        verify(abilityRepository).searchByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AbilityService#searchByName(String)}
     */
    @Test
    void testSearchByName2() {
        MyPokemon pokemon = new MyPokemon();
        pokemon.setAbilities(new ArrayList<>());
        pokemon.setFood(new HashSet<>());
        pokemon.setId(1L);
        pokemon.setName(" ");
        pokemon.setUrl("https://example.org/example");

        Ability ability = new Ability();
        ability.setDescription("The characteristics of someone or something");
        ability.setId(1L);
        ability.setName(" ");
        ability.setPokemon(pokemon);

        ArrayList<Ability> abilityList = new ArrayList<>();
        abilityList.add(ability);
        when(abilityRepository.searchByName(Mockito.<String>any())).thenReturn(abilityList);
        List<Ability> actualSearchByNameResult = abilityService.searchByName("Name");
        verify(abilityRepository).searchByName(Mockito.<String>any());
        assertEquals(1, actualSearchByNameResult.size());
    }

    /**
     * Method under test: {@link AbilityService#searchByName(String)}
     */
    @Test
    void testSearchByName3() {
        assertThrows(BadRequestException.class, () -> abilityService.searchByName(" "));
    }

    /**
     * Method under test: {@link AbilityService#searchByName(String)}
     */
    @Test
    void testSearchByName4() {
        assertThrows(BadRequestException.class, () -> abilityService.searchByName(null));
    }

    /**
     * Method under test: {@link AbilityService#searchByName(String)}
     */
    @Test
    void testSearchByName5() {
        when(abilityRepository.searchByName(Mockito.<String>any())).thenThrow(new BadRequestException("An error occurred"));
        assertThrows(ServerException.class, () -> abilityService.searchByName("Name"));
        verify(abilityRepository).searchByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AbilityService#insertAbility(Ability, Long)}
     */
    @Test
    void testInsertAbility() {
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
        when(abilityRepository.save(Mockito.<Ability>any())).thenReturn(ability);

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

        MyPokemon pokemon2 = new MyPokemon();
        pokemon2.setAbilities(new ArrayList<>());
        pokemon2.setFood(new HashSet<>());
        pokemon2.setId(1L);
        pokemon2.setName("Name");
        pokemon2.setUrl("https://example.org/example");

        Ability ability2 = new Ability();
        ability2.setDescription("The characteristics of someone or something");
        ability2.setId(1L);
        ability2.setName("Name");
        ability2.setPokemon(pokemon2);
        abilityService.insertAbility(ability2, 1L);
        verify(pokemonRepository).searchById(Mockito.<Long>any());
        verify(pokemonRepository).findById(Mockito.<Long>any());
        verify(abilityRepository).save(Mockito.<Ability>any());
        assertSame(myPokemon2, ability2.getPokemon());
    }

    /**
     * Method under test: {@link AbilityService#insertAbility(Ability, Long)}
     */
    @Test
    void testInsertAbility2() {
        MyPokemon myPokemon = new MyPokemon();
        myPokemon.setAbilities(new ArrayList<>());
        myPokemon.setFood(new HashSet<>());
        myPokemon.setId(1L);
        myPokemon.setName("Name");
        myPokemon.setUrl("https://example.org/example");
        Optional<MyPokemon> ofResult = Optional.of(myPokemon);
        when(pokemonRepository.searchById(Mockito.<Long>any())).thenThrow(new BadRequestException("An error occurred"));
        when(pokemonRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        assertThrows(ServerException.class, () -> abilityService.insertAbility(ability, 1L));
        verify(pokemonRepository).searchById(Mockito.<Long>any());
        verify(pokemonRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AbilityService#insertAbility(Ability, Long)}
     */
    @Test
    void testInsertAbility3() {
        when(pokemonRepository.findById(Mockito.<Long>any())).thenReturn(null);

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
        assertThrows(ServerException.class, () -> abilityService.insertAbility(ability, 1L));
        verify(pokemonRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AbilityService#insertAbility(Ability, Long)}
     */
    @Test
    void testInsertAbility4() {
        Optional<MyPokemon> emptyResult = Optional.empty();
        when(pokemonRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

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
        assertThrows(NotFoundException.class, () -> abilityService.insertAbility(ability, 1L));
        verify(pokemonRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AbilityService#insertAbility(Ability, Long)}
     */
    @Test
    void testInsertAbility5() {
        MyPokemon pokemon = new MyPokemon();
        pokemon.setAbilities(new ArrayList<>());
        pokemon.setFood(new HashSet<>());
        pokemon.setId(1L);
        pokemon.setName("Name");
        pokemon.setUrl("https://example.org/example");

        Ability ability = new Ability();
        ability.setDescription("The characteristics of someone or something");
        ability.setId(1L);
        ability.setName(" ");
        ability.setPokemon(pokemon);
        assertThrows(BadRequestException.class, () -> abilityService.insertAbility(ability, 1L));
    }

    /**
     * Method under test: {@link AbilityService#insertAbility(Ability, Long)}
     */
    @Test
    void testInsertAbility6() {
        MyPokemon pokemon = new MyPokemon();
        pokemon.setAbilities(new ArrayList<>());
        pokemon.setFood(new HashSet<>());
        pokemon.setId(1L);
        pokemon.setName("Name");
        pokemon.setUrl("https://example.org/example");

        Ability ability = new Ability();
        ability.setDescription("The characteristics of someone or something");
        ability.setId(1L);
        ability.setName(null);
        ability.setPokemon(pokemon);
        assertThrows(BadRequestException.class, () -> abilityService.insertAbility(ability, 1L));
    }

    /**
     * Method under test: {@link AbilityService#deleteAbilityById(Long)}
     */
    @Test
    void testDeleteAbilityById() {
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
        Optional<Ability> ofResult = Optional.of(ability);
        doNothing().when(abilityRepository).deleteById(Mockito.<Long>any());
        when(abilityRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        abilityService.deleteAbilityById(1L);
        verify(abilityRepository).deleteById(Mockito.<Long>any());
        verify(abilityRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AbilityService#deleteAbilityById(Long)}
     */
    @Test
    void testDeleteAbilityById2() {
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
        Optional<Ability> ofResult = Optional.of(ability);
        doThrow(new BadRequestException("An error occurred")).when(abilityRepository).deleteById(Mockito.<Long>any());
        when(abilityRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(ServerException.class, () -> abilityService.deleteAbilityById(1L));
        verify(abilityRepository).deleteById(Mockito.<Long>any());
        verify(abilityRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AbilityService#deleteAbilityById(Long)}
     */
    @Test
    void testDeleteAbilityById3() {
        Optional<Ability> emptyResult = Optional.empty();
        when(abilityRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(NotFoundException.class, () -> abilityService.deleteAbilityById(1L));
        verify(abilityRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AbilityService#updateAbility(Ability, Long)}
     */
    @Test
    void testUpdateAbility() {
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
        Optional<Ability> ofResult = Optional.of(ability);

        MyPokemon pokemon2 = new MyPokemon();
        pokemon2.setAbilities(new ArrayList<>());
        pokemon2.setFood(new HashSet<>());
        pokemon2.setId(1L);
        pokemon2.setName("Name");
        pokemon2.setUrl("https://example.org/example");

        Ability ability2 = new Ability();
        ability2.setDescription("The characteristics of someone or something");
        ability2.setId(1L);
        ability2.setName("Name");
        ability2.setPokemon(pokemon2);
        when(abilityRepository.save(Mockito.<Ability>any())).thenReturn(ability2);
        when(abilityRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        MyPokemon pokemon3 = new MyPokemon();
        pokemon3.setAbilities(new ArrayList<>());
        pokemon3.setFood(new HashSet<>());
        pokemon3.setId(1L);
        pokemon3.setName("Name");
        pokemon3.setUrl("https://example.org/example");

        Ability ability3 = new Ability();
        ability3.setDescription("The characteristics of someone or something");
        ability3.setId(1L);
        ability3.setName("Name");
        ability3.setPokemon(pokemon3);
        abilityService.updateAbility(ability3, 1L);
        verify(abilityRepository, atLeast(1)).findById(Mockito.<Long>any());
        verify(abilityRepository).save(Mockito.<Ability>any());
        assertEquals(1L, ability3.getId().longValue());
    }

    /**
     * Method under test: {@link AbilityService#updateAbility(Ability, Long)}
     */
    @Test
    void testUpdateAbility2() {
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
        Optional<Ability> ofResult = Optional.of(ability);
        when(abilityRepository.save(Mockito.<Ability>any())).thenThrow(new BadRequestException("An error occurred"));
        when(abilityRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        MyPokemon pokemon2 = new MyPokemon();
        pokemon2.setAbilities(new ArrayList<>());
        pokemon2.setFood(new HashSet<>());
        pokemon2.setId(1L);
        pokemon2.setName("Name");
        pokemon2.setUrl("https://example.org/example");

        Ability ability2 = new Ability();
        ability2.setDescription("The characteristics of someone or something");
        ability2.setId(1L);
        ability2.setName("Name");
        ability2.setPokemon(pokemon2);
        assertThrows(ServerException.class, () -> abilityService.updateAbility(ability2, 1L));
        verify(abilityRepository, atLeast(1)).findById(Mockito.<Long>any());
        verify(abilityRepository).save(Mockito.<Ability>any());
    }

    /**
     * Method under test: {@link AbilityService#updateAbility(Ability, Long)}
     */
    @Test
    void testUpdateAbility3() {
        Optional<Ability> emptyResult = Optional.empty();
        when(abilityRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

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
        assertThrows(NotFoundException.class, () -> abilityService.updateAbility(ability, 1L));
        verify(abilityRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AbilityService#searchByPokemonName(String)}
     */
    @Test
    void testSearchByPokemonName() {
        when(pokemonRepository.searchByName(Mockito.<String>any())).thenReturn(new ArrayList<>());
        assertThrows(NotFoundException.class, () -> abilityService.searchByPokemonName("Name"));
        verify(pokemonRepository).searchByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AbilityService#searchByPokemonName(String)}
     */
    @Test
    void testSearchByPokemonName2() {
        ArrayList<Ability> abilityList = new ArrayList<>();
        when(abilityRepository.searchAbilitiesByPokemon(Mockito.<String>any())).thenReturn(abilityList);

        MyPokemon myPokemon = new MyPokemon();
        myPokemon.setAbilities(new ArrayList<>());
        myPokemon.setFood(new HashSet<>());
        myPokemon.setId(1L);
        myPokemon.setName(" ");
        myPokemon.setUrl("https://example.org/example");

        ArrayList<MyPokemon> myPokemonList = new ArrayList<>();
        myPokemonList.add(myPokemon);
        when(pokemonRepository.searchByName(Mockito.<String>any())).thenReturn(myPokemonList);
        List<Ability> actualSearchByPokemonNameResult = abilityService.searchByPokemonName("Name");
        verify(abilityRepository).searchAbilitiesByPokemon(Mockito.<String>any());
        verify(pokemonRepository).searchByName(Mockito.<String>any());
        assertTrue(actualSearchByPokemonNameResult.isEmpty());
        assertSame(abilityList, actualSearchByPokemonNameResult);
    }

    /**
     * Method under test: {@link AbilityService#searchByPokemonName(String)}
     */
    @Test
    void testSearchByPokemonName3() {
        assertThrows(BadRequestException.class, () -> abilityService.searchByPokemonName(" "));
    }

    /**
     * Method under test: {@link AbilityService#searchByPokemonName(String)}
     */
    @Test
    void testSearchByPokemonName4() {
        assertThrows(BadRequestException.class, () -> abilityService.searchByPokemonName(null));
    }

    /**
     * Method under test: {@link AbilityService#searchByPokemonName(String)}
     */
    @Test
    void testSearchByPokemonName5() {
        when(abilityRepository.searchAbilitiesByPokemon(Mockito.<String>any()))
                .thenThrow(new BadRequestException("An error occurred"));

        MyPokemon myPokemon = new MyPokemon();
        myPokemon.setAbilities(new ArrayList<>());
        myPokemon.setFood(new HashSet<>());
        myPokemon.setId(1L);
        myPokemon.setName(" ");
        myPokemon.setUrl("https://example.org/example");

        ArrayList<MyPokemon> myPokemonList = new ArrayList<>();
        myPokemonList.add(myPokemon);
        when(pokemonRepository.searchByName(Mockito.<String>any())).thenReturn(myPokemonList);
        assertThrows(ServerException.class, () -> abilityService.searchByPokemonName("Name"));
        verify(abilityRepository).searchAbilitiesByPokemon(Mockito.<String>any());
        verify(pokemonRepository).searchByName(Mockito.<String>any());
    }
}
