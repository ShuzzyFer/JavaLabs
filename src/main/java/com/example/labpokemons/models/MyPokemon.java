package com.example.labpokemons.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "pokemons")
@Setter
@Getter
@Schema(name = "Pokemon")
public class MyPokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "Name", example = "Pikachu")
    private String name;

    @Schema(description = "URL")
    private String url;

    @OneToMany(mappedBy = "pokemon", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Ability> abilities;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pokemons")
    private Set<Food> food = new HashSet<>();

}
