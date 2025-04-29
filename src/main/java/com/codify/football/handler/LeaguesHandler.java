package com.codify.football.handler;

import com.codify.football.api.LeaguesApiDelegate;
import com.codify.football.model.League;
import com.codify.football.services.LeagueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;

@Controller
public class LeaguesHandler implements LeaguesApiDelegate {
    private final LeagueService service;

    public LeaguesHandler(LeagueService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<List<League>> getLeagues() {
        List<League> leagues = this.service.getAllLeagues();
        if (leagues.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        return ResponseEntity.ok(leagues);

    }
}
