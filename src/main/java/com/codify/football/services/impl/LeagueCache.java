package com.codify.football.services.impl;

import com.codify.football.model.League;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LeagueCache {

    private static Map<String, List<League>> cacheLeagueMap = new HashMap<>();

    public static List<League> getCacheLeagueMap(String key) {
        return cacheLeagueMap.getOrDefault(key, Collections.emptyList());
    }

    public static void setCacheLeagueMap(String key, List<League> league) {
        cacheLeagueMap.put(key,league);
    }
}
