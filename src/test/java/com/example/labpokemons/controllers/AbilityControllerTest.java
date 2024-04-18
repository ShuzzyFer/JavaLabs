package com.example.labpokemons.controllers;

import com.example.labpokemons.models.Ability;
import com.example.labpokemons.services.AbilityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AbilityControllerTest {
    @Mock
    private AbilityService abilityService;

    @InjectMocks
    private AbilityController abilityController;

    private MockMvc mockMvc;

    private static final Ability ability = new Ability();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(abilityController).build();

        ability.setId(1L);
        ability.setName("abobaaboba");
        ability.setDescription("aboooba");
    }

    @Test
    void getAbilityTest() throws Exception {
        mockMvc.perform(get("/getAbility/abobaaboba")).andExpect(status().isOk());
    }
}
