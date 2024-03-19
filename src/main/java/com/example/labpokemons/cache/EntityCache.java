package com.example.labpokemons.cache;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class EntityCache<Key, Value> {
    private final Map<Key, Value> cache = new ConcurrentHashMap<>();

    public void put(Key key, Value value) {
        cache.put(key, value);
    }

    public Value get(Key key) {
        return cache.get(key);
    }

    public void remove(Key key) {
        cache.remove(key);
    }

    public void clear() {
        cache.clear();
    }
}
