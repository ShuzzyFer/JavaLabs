package com.example.labpokemons.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class EntityCacheTest {
    private EntityCache<String, Integer> cache;

    @BeforeEach
    public void setup() {
        cache = new EntityCache<>();
    }

    @Test
    void testPutAndGet() {
        cache.put("key1", 1);
        assertEquals(1, cache.get("key1"));
    }

    @Test
    void testRemove() {
        cache.put("key2", 2);
        cache.remove("key2");
        assertNull(cache.get("key2"));
    }

    @Test
    void testClear() {
        cache.put("key3", 3);
        cache.clear();
        assertNull(cache.get("key3"));
    }

}
