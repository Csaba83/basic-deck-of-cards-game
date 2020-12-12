package com.bdocg.service;

import com.bdocg.domain.Game;
import com.bdocg.repository.GameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

    private static final String GAME_NAME = "testGame";
    @Mock
    private GameRepository mockGameRepository;

    @InjectMocks
    private GameService gameService;

    @Test
    public void createGame() {
        Game game = new Game(GAME_NAME);

        gameService.createGame(GAME_NAME);

        verify(mockGameRepository).save(game);
    }

    @Test
    public void deleteGame() {
        Game game = new Game(GAME_NAME);
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.of(game));

        gameService.deleteGame(GAME_NAME);

        verify(mockGameRepository).delete(game);
    }

    @Test
    public void deleteNonExistentGame() {
        Game game = new Game(GAME_NAME);
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.empty());

        gameService.deleteGame(GAME_NAME);

        verify(mockGameRepository, times(0)).delete(any());
    }


}