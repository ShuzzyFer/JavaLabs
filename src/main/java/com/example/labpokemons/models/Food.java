package com.example.labpokemons.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "food")
@Setter
@Getter
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "Name", example = "Meat")
    private String name;

    @Schema(description = "Description", example = "Yummy")
    private String description;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @JoinTable(name = "pokemon_food",
            joinColumns = @JoinColumn(name = "food_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "pokemon_id",
                    referencedColumnName = "id"))
    private Set<MyPokemon> pokemons = new HashSet<>();

}
