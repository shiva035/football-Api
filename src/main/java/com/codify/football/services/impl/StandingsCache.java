package com.codify.football.services.impl;

import com.codify.football.model.Standings;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StandingsCache {
    private static final Map<String, List<Standings>> cache = new HashMap<>();

    public static void setCache(String key, List<Standings> standings) {
        cache.put(key, standings);
    }

    public static List<Standings> get(String key) {
        return cache.getOrDefault(key, Collections.emptyList());
    }
}
