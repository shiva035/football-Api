package com.codify.football.services;

import com.codify.football.model.Standings;

import java.util.List;

public interface StandingService {
    public List<Standings> getStandings(String countryName, String leagueName, String teamName);
}
