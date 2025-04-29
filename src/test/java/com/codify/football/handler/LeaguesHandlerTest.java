package com.codify.football.handler;

import com.codify.football.model.League;
import com.codify.football.services.LeagueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeaguesHandlerTest {

    @InjectMocks
    private LeaguesHandler leaguesHandler;

    @Mock
    private LeagueService service;

    private List<League> mockLeagues;

    @BeforeEach
    public void setUp(){
        mockLeagues = Arrays.asList(new League().leagueId("1").leagueName("Test1"), new League().leagueId("2").leagueName("Test2"));
    }

    @Test
    void getLeagues_nonEmptyList_returnsOkWithLeagues() {
        // Arrange
        when(service.getAllLeagues()).thenReturn(mockLeagues);

        // Act
        ResponseEntity<List<League>> response = leaguesHandler.getLeagues();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockLeagues, response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Test1", response.getBody().get(0).getLeagueName());
    }

    @Test
    void getLeagues_emptyList_returnsNotFoundWithEmptyList() {
        // Arrange
        when(service.getAllLeagues()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<League>> response = leaguesHandler.getLeagues();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
    }
}