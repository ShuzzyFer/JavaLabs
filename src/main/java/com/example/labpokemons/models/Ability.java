package com.example.labpokemons.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "abilities")
@Getter
@Setter
@Schema(name = "Ability")
public class Ability {
    @Schema(description = "name")
    private String name;

    @Schema(description = "Description")
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pokemon_id")
    @JsonIgnore
    private MyPokemon pokemon;

}
