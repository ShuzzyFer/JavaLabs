package com.example.labpokemons.services;

import com.example.labpokemons.models.PokemonListResponse;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OkHttpRequest {
    public OkHttpRequest() {
        //empty cause of logger
    }

    private static Gson gson = new Gson();
    private static final String BASE_URL = "https://pokeapi.co/api/v2/pokemon/";

    public static PokemonListResponse get(final String endpoint)
            throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(BASE_URL + '?' + endpoint)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to fetch data: " + response
                        .code());
            }
            return gson.fromJson(response.body()
                    .string(), PokemonListResponse.class);
        }
    }
}
