package com.example.labpokemons.models;

import java.util.List;

public class PokemonListResponse {
    private int count;
    private String next;
    private String previous;
    private List<Pokemon> results;
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String getNext() {
        return next;
    }
    public void setNext(String next) {
        this.next = next;
    }
    public String getPrevious() {
        return previous;
    }
    public void setPrevious(String previous) {
        this.previous = previous;
    }
    public List<Pokemon> getPokemons() {
        return results;
    }
    public void setPokemons(List<Pokemon> pokemons) {
        this.results = pokemons;
    }
    public PokemonListResponse(int count, String next, String previous, List<Pokemon> pokemons) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = pokemons;
    }
}
