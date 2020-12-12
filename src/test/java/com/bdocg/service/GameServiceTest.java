package com.bdocg.service;

import com.bdocg.domain.Card;
import com.bdocg.domain.Deck;
import com.bdocg.domain.Game;
import com.bdocg.repository.GameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

    private static final String GAME_NAME = "testGameName";
    private static final String DECK_NAME = "testDeckName";

    @Mock
    private GameRepository mockGameRepository;

    @Mock
    private DeckService mockDeckService;

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
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.empty());

        gameService.deleteGame(GAME_NAME);

        verify(mockGameRepository, times(0)).delete(any());
    }

    @Test
    public void addDeckToGame() {
        Deck deck = new Deck(DECK_NAME, Arrays.asList(Card.values()));
        Game game = new Game(GAME_NAME);
        Game gameToSave = new Game(GAME_NAME);
        gameToSave.addDeck(deck);
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.of(game));
        when(mockDeckService.getDeck(DECK_NAME)).thenReturn(deck);

        gameService.addDeckToGame(GAME_NAME, DECK_NAME);

        verify(mockGameRepository).save(gameToSave);
    }

    @Test(expected = NotFoundException.class)
    public void addDeckToNonExistentGame() {
        Deck deck = new Deck(DECK_NAME, Arrays.asList(Card.values()));
        Game gameToSave = new Game(GAME_NAME);
        gameToSave.addDeck(deck);
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.empty());

        gameService.addDeckToGame(GAME_NAME, DECK_NAME);
    }
}