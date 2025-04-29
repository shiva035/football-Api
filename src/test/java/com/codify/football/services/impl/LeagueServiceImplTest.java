package com.codify.football.services.impl;

import com.codify.football.config.ConfigProperties;
import com.codify.football.model.League;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
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
class LeagueServiceImplTest {

    @Mock
    private ConfigProperties configProperties;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private LeagueServiceImpl leagueService;

    private League[] mockLeagues;

    @BeforeEach
    void setUp() {
        mockLeagues = new League[]{
                new League().leagueId("1").leagueName("Premier League"),
                new League().leagueId("2").leagueName("La Liga")
        };
    }

    @Test
    void getAllLeagues_successfulApiCall_returnsLeagues() {
        // Arrange
        ResponseEntity<League[]> responseEntity = new ResponseEntity<>(mockLeagues, HttpStatus.OK);
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        )).thenReturn(responseEntity);

        // Act
        List<League> result = leagueService.getAllLeagues();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Premier League", result.get(0).getLeagueName());
    }

    @Test
    void getAllLeagues_apiFailure_returnsCachedLeagues() {
        List<League> cachedLeagues = Arrays.asList(new League().leagueId("3").leagueName("Serie A"));
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        )).thenThrow(new RestClientException("API unavailable"));

        try (var mockedStatic = mockStatic(LeagueCache.class)) {
            mockedStatic.when(() -> LeagueCache.getCacheLeagueMap("league-data")).thenReturn(cachedLeagues);
            List<League> result = leagueService.getAllLeagues();
            assertEquals(1, result.size());
        }
    }

    @Test
    void getAllLeagues_unexpectedError_returnsEmptyList() {

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        )).thenThrow(new RuntimeException("Unexpected error"));

        // Mock static methods
        try (var mockedStatic = mockStatic(LeagueCache.class)) {
            // Act
            List<League> result = leagueService.getAllLeagues();

            // Assert
            assertEquals(0, result.size());
            mockedStatic.verifyNoInteractions();
        }
    }
}