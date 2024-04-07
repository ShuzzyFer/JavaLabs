package com.example.labpokemons.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@Schema(name = "PokemonListResponse")
public class PokemonListResponse {
    @Schema(description = "Count")
    private int count;

    @Schema(description = "Next")
    private String next;

    @Schema(description = "Previous")
    private String previous;

    @Schema(description = "Results")
    private List<MyPokemon> results;

}
