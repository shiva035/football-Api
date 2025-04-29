package com.codify.football.handler;

import com.codify.football.model.Standings;
import com.codify.football.services.StandingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StandingHandlerTest {

    @InjectMocks
    private StandingHandler standingHandler;

    @Mock
    private StandingService service;

    List<Standings> mockStanding;

    @BeforeEach
    public void setup(){
        mockStanding = Arrays.asList(new Standings().leagueId("1").leagueName("Test1"), new Standings().leagueId("2").leagueName("Test2"));
    }

    @Test
    void getStandings_nonEmptyList_returnsOkWithStandings() {
        when(service.getStandings("testCountry", "Premier League", "testName")).thenReturn(mockStanding);

        ResponseEntity<List<Standings>> responseEntity = standingHandler.getStandings("testCountry", "Premier League", "testName");

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockStanding, responseEntity.getBody());
        assertEquals(2, responseEntity.getBody().size());
        assertEquals("Test1", responseEntity.getBody().get(0).getLeagueName());
    }

    @Test
    void getStandings_emptyList_returnsNotFoundWithEmptyList() {
        when(service.getStandings("testCountry", "Premier League", "testName")).thenReturn(Collections.emptyList());

        ResponseEntity<List<Standings>> responseEntity = standingHandler.getStandings("testCountry", "Premier League", "testName");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(Collections.emptyList(), responseEntity.getBody());
    }
}