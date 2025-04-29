package com.codify.football.handler;

import com.codify.football.api.StandingsApiDelegate;
import com.codify.football.model.Standings;
import com.codify.football.services.StandingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
public class StandingHandler implements StandingsApiDelegate {

    private final StandingService service;

    public StandingHandler(StandingService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<List<Standings>> getStandings(String countryName, String leagueName, String teamName) {
        List<Standings> standings = this.service.getStandings(countryName, leagueName, teamName);
        if(standings.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        return ResponseEntity.ok(standings);
    }


}
