package com.example.labpokemons.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="abilities")
public class Ability {
    String name;
    String Description;
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
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
