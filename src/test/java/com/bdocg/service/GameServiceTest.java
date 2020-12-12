package com.bdocg.service;

import com.bdocg.domain.Card;
import com.bdocg.domain.Deck;
import com.bdocg.domain.Game;
import com.bdocg.domain.Player;
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
    private static final String PLAYER_NAME = "testPlayerName";

    @Mock
    private GameRepository mockGameRepository;

    @Mock
    private DeckService mockDeckService;

    @Mock
    private PlayerService mockPlayerService;

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

    @Test
    public void addPlayerToGame() {
        Player player = new Player(PLAYER_NAME);
        Game game = new Game(GAME_NAME);
        Game gameToSave = new Game(GAME_NAME);
        gameToSave.addPlayer(player);
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.of(game));
        when(mockPlayerService.createPlayer(PLAYER_NAME)).thenReturn(player);

        gameService.addPlayerToGame(GAME_NAME, PLAYER_NAME);

        verify(mockGameRepository).save(gameToSave);
    }

    @Test(expected = NotFoundException.class)
    public void addPlayerToNonExistentGame() {
        Player player = new Player(PLAYER_NAME);
        Game gameToSave = new Game(GAME_NAME);
        gameToSave.addPlayer(player);
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.empty());

        gameService.addPlayerToGame(GAME_NAME, PLAYER_NAME);
    }

    @Test
    public void removePlayerFromGame() {
        Game game = new Game(GAME_NAME);
        Game gameToSave = new Game(GAME_NAME);
        gameToSave.removePlayer(PLAYER_NAME);
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.of(game));

        gameService.removePlayerFromGame(GAME_NAME, PLAYER_NAME);

        verify(mockGameRepository).save(gameToSave);
    }

    @Test(expected = NotFoundException.class)
    public void removePlayerFromNonExistentGame() {
        Game gameToSave = new Game(GAME_NAME);
        gameToSave.removePlayer(PLAYER_NAME);
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.empty());

        gameService.addPlayerToGame(GAME_NAME, PLAYER_NAME);
    }
}