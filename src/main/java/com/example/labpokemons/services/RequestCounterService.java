package com.example.labpokemons.services;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RequestCounterService {
    AtomicInteger requestCounter = new AtomicInteger(0);

    public int increment() {
        return requestCounter.incrementAndGet();
    }

    public int get() {
        return requestCounter.get();
    }

    public void reset() {
        requestCounter.set(0);
    }
}
