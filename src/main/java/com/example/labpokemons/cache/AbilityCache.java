package com.example.labpokemons.cache;

import com.example.labpokemons.models.Ability;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AbilityCache {
    private final Map<String, List<Ability>> cache = new ConcurrentHashMap<>();

    public void put(String key, List<Ability> value) {
        cache.put(key, value);
    }

    public List<Ability> get(String key) {
        return cache.get(key);
    }

    public void remove(String key) {
        cache.remove(key);
    }

    public void clear() {
        cache.clear();
    }
}
