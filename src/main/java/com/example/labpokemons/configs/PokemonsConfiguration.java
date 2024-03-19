package com.example.labpokemons.configs;

import com.example.labpokemons.cache.EntityCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PokemonsConfiguration {
    @Bean
    public EntityCache AbilityCache() {
        return new EntityCache();
    }
}
