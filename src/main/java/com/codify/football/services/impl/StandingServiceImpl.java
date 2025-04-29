package com.codify.football.services.impl;

import com.codify.football.common.LeaguesEnum;
import com.codify.football.config.ConfigProperties;
import com.codify.football.model.League;
import com.codify.football.model.Standings;
import com.codify.football.services.StandingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class StandingServiceImpl implements StandingService {
    @Autowired
    private ConfigProperties properties;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StandingsCache standingsCache;


    @Override
    public List<Standings> getStandings(String countryName, String leagueName, String teamName) {
        List<Standings> standingsList = new ArrayList<>();
        try{
            Optional<Integer> optionalIid = LeaguesEnum.getLeagueIdByName(leagueName);
            if(optionalIid.isPresent()){
                String sbUrlParam = "?action=get_standings&league_id=" + optionalIid.get() + "&APIkey=" + properties.getApiKey();
                String url = properties.getApiUrl() + sbUrlParam;
                ResponseEntity<Standings[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Standings[]>() {});
                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    standingsList.addAll(List.of(responseEntity.getBody()));
                    if(!countryName.isBlank()){
                        standingsList = standingsList.stream().filter(d->d.getCountryName().equals(countryName)).collect(Collectors.toList());
                    }
                    if (!teamName.isBlank()){
                        standingsList = standingsList.stream().filter(d->d.getTeamName().equals(teamName)).collect(Collectors.toList());
                    }
                    StandingsCache.setCache((leagueName.replaceAll("\\s+", "") + "_" + countryName + "_" + teamName), standingsList);
                }else {
                    log.info("API responded with status: {}", responseEntity.getStatusCode());
                }
            }else {
                log.info("Invalid League id");
            }
        } catch (RestClientException ex) {
            standingsList =  StandingsCache.get((leagueName.replaceAll("\\s+", "") + "_" + countryName + "_" + teamName));
            log.error("Error while fetching standings: {}", "API is unavailable");
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        }

        return standingsList;
    }



}
