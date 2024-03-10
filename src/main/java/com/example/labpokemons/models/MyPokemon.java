package com.example.labpokemons.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "pokemons")
public class MyPokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String url;

    @OneToMany(mappedBy = "pokemon", cascade = CascadeType.MERGE)
    private List<Ability> abilities;

    public MyPokemon() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public MyPokemon(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
