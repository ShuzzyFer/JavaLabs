package com.example.labpokemons.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="abilities")
public class Ability {
    String name;
    String description;
    @Id
    private Long id;
    @ManyToOne // This defines the ManyToOne relationship (opposite side of OneToMany)
    @JoinColumn(name = "pokemon_id") // This specifies the foreign key column
    @JsonIgnore
    private MyPokemon pokemon;

    public MyPokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(MyPokemon pokemon) {
        this.pokemon = pokemon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
