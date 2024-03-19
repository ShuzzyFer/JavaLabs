package com.example.labpokemons.configs;

import com.example.labpokemons.cache.AbilityCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PokemonsConfiguration {
    @Bean
    public AbilityCache inMemoryCache() {
        return new AbilityCache();
    }
}
