package com.codify.football.services.impl;

import com.codify.football.config.ConfigProperties;
import com.codify.football.model.Standings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StandingServiceImplTest {

    @Mock
    private ConfigProperties properties;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private StandingServiceImpl service;

    private Standings[] mockStandings;

    @BeforeEach
    public void setUp(){
        mockStandings = new Standings[] {
                new Standings().leagueId("1").leagueName("Premier League").countryName("England").teamName("Test1"),
                new Standings().leagueId("2").leagueName("Non Premier League").countryName("France").teamName("Test2")
        };
    }

    @Test
    void getStandings_successfulApiCall_returnsStandings() {

        ResponseEntity<Standings[]> responseEntity = new ResponseEntity<>(mockStandings, HttpStatus.OK);
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        )).thenReturn(responseEntity);

       List<Standings> result =  service.getStandings(null,"Premier League",null);

        assertEquals(2, result.size());
        assertEquals("Premier League", result.get(0).getLeagueName());
    }

    @Test
    void getStandings_apiFailure_returnsCachedStandings() {
        List<Standings> cacheStanding = Arrays.asList(new Standings().leagueId("3").leagueName("TestLeague"));
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        )).thenThrow(new RestClientException("API unavailable"));

        try(var mockedStatic = mockStatic(StandingsCache.class)){
            mockedStatic.when(() -> StandingsCache.get("PremierLeague_Country_Team")).thenReturn(cacheStanding);
            List<Standings> result =  service.getStandings("Country","Premier League","Team");
            assertEquals(1, result.size());
        }
    }

    @Test
    void getStandings_unexpectedError_returnsEmptyList() {
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        )).thenThrow(new RuntimeException("Unexpected error"));

        try(var mockedStatic = mockStatic(StandingsCache.class)){
            List<Standings> result =  service.getStandings("Country","Premier League","Team");
            assertEquals(0, result.size());
            mockedStatic.verifyNoInteractions();
        }

    }

}