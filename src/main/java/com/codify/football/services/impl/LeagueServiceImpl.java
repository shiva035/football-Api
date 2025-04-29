package com.codify.football.services.impl;

import com.codify.football.config.ConfigProperties;
import com.codify.football.model.League;
import com.codify.football.services.LeagueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LeagueServiceImpl implements LeagueService {
    @Autowired
    private ConfigProperties properties;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public List<League> getAllLeagues() {
        List<League> leaguesResp = new ArrayList<>();
        try {
            String url = properties.getApiUrl() + "?action=get_leagues&APIkey=" + properties.getApiKey();
            ResponseEntity<League[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<League[]>() {});
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                leaguesResp.addAll(List.of(responseEntity.getBody()));
                //cache data
                LeagueCache.setCacheLeagueMap("league-data", leaguesResp);
            } else {
                log.warn("API responded with status: {}", responseEntity.getStatusCode());
            }
        } catch (RestClientException ex) {
            leaguesResp = LeagueCache.getCacheLeagueMap("league-data");
            log.error("Error while fetching leagues: {}", "API is unavailable");

        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        }
        return leaguesResp;
    }

}
