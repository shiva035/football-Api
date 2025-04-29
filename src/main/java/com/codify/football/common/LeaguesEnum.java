package com.codify.football.common;

import java.util.Arrays;
import java.util.Optional;

public enum LeaguesEnum {
    PREMIER_LEAGUE(177, "Premier League"),
    NON_PREMIER_LEAGUE(149, "Non League Premier");

    private final int leagueId;
    private final String leagueName;

    LeaguesEnum(int leagueId, String leagueName) {
        this.leagueId = leagueId;
        this.leagueName = leagueName;
    }

    // Getter for leagueId
    public int getLeagueId() {
        return leagueId;
    }

    // Getter for leagueName
    public String getLeagueName() {
        return leagueName;
    }

    // Lookup method to get league name by ID
    public static Optional<String> getLeagueNameById(int id) {
        return Arrays.stream(values())
                .filter(league -> league.leagueId == id)
                .map(LeaguesEnum::getLeagueName)
                .findFirst();
    }

    // Lookup method to get league ID by name
    public static Optional<Integer> getLeagueIdByName(String name) {
        return Arrays.stream(values())
                .filter(league -> league.leagueName.equals(name))
                .map(LeaguesEnum::getLeagueId)
                .findFirst();
    }
}
