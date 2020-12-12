package com.bdocg.service;

import com.bdocg.domain.Card;
import com.bdocg.domain.Deck;
import com.bdocg.domain.Game;
import com.bdocg.domain.Player;
import com.bdocg.repository.GameRepository;
import com.bdocg.view.PlayerView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
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

    private Game game, gameToSave;
    private Deck deck, emptyDeck;
    private Player playerWithNoCard, playerWithOneCard;

    @Before
    public void setUp() {
        game = new Game(GAME_NAME);
        deck = new Deck(DECK_NAME, Collections.singletonList(Card.CLUB_A));
        emptyDeck = new Deck(DECK_NAME, Collections.emptyList());
        gameToSave = new Game(GAME_NAME);
        playerWithNoCard = new Player(PLAYER_NAME);
        playerWithOneCard = new Player(PLAYER_NAME);
        playerWithOneCard.addCard(Card.CLUB_A);
    }

    @Test
    public void createGame() {
        gameService.createGame(GAME_NAME);

        verify(mockGameRepository).save(game);
    }

    @Test
    public void deleteGame() {
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
        gameToSave.addDeck(deck);
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.of(game));
        when(mockDeckService.getDeck(DECK_NAME)).thenReturn(deck);

        gameService.addDeckToGame(GAME_NAME, DECK_NAME);

        verify(mockGameRepository).save(gameToSave);
    }

    @Test(expected = NotFoundException.class)
    public void addDeckToNonExistentGame() {
        gameToSave.addDeck(deck);
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.empty());

        gameService.addDeckToGame(GAME_NAME, DECK_NAME);
    }

    @Test
    public void addPlayerToGame() {
        gameToSave.addPlayer(playerWithNoCard);
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.of(game));
        when(mockPlayerService.createPlayer(PLAYER_NAME)).thenReturn(playerWithNoCard);

        gameService.addPlayerToGame(GAME_NAME, PLAYER_NAME);

        verify(mockGameRepository).save(gameToSave);
    }

    @Test(expected = NotFoundException.class)
    public void addPlayerToNonExistentGame() {
        gameToSave.addPlayer(playerWithNoCard);
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.empty());

        gameService.addPlayerToGame(GAME_NAME, PLAYER_NAME);
    }

    @Test
    public void removePlayerFromGame() {
        gameToSave.removePlayer(PLAYER_NAME);
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.of(game));

        gameService.removePlayerFromGame(GAME_NAME, PLAYER_NAME);

        verify(mockGameRepository).save(gameToSave);
    }

    @Test(expected = NotFoundException.class)
    public void removePlayerFromNonExistentGame() {
        gameToSave.removePlayer(PLAYER_NAME);
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.empty());

        gameService.addPlayerToGame(GAME_NAME, PLAYER_NAME);
    }

    @Test
    public void dealCardsToPlayer() {
        game.addDeck(deck);
        game.addPlayer(playerWithNoCard);
        gameToSave.addDeck(emptyDeck);
        gameToSave.addPlayer(playerWithOneCard);
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.of(game));

        gameService.dealCardsToPlayer(GAME_NAME, PLAYER_NAME, 1);

        verify(mockGameRepository).save(gameToSave);
    }

    @Test
    public void NoCardIsDealtIfNotEnoughCardInTheShoe() {
        game.addDeck(emptyDeck);
        game.addPlayer(playerWithNoCard);
        gameToSave.addDeck(emptyDeck);
        gameToSave.addPlayer(playerWithNoCard);
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.of(game));

        gameService.dealCardsToPlayer(GAME_NAME, PLAYER_NAME, 1);

        verify(mockGameRepository, times(0)).save(any());
    }

    @Test(expected = NotFoundException.class)
    public void dealCardsToPlayerInNonExistentGame() {
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.empty());

        gameService.dealCardsToPlayer(GAME_NAME, PLAYER_NAME, 1);
    }

    @Test(expected = NotFoundException.class)
    public void dealCardsToNonExistentPlayer() {
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.of(game));

        gameService.dealCardsToPlayer(GAME_NAME, PLAYER_NAME, 1);
    }

    @Test(expected = NotFoundException.class)
    public void getPlayersInNonExistentGame() {
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.empty());

        gameService.getPlayersInGame(GAME_NAME);
    }

    @Test
    public void getPlayersInGame() {
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        player1.addCard(Card.CLUB_10);
        player1.addCard(Card.CLUB_K);
        player2.addCard(Card.CLUB_7);
        player2.addCard(Card.CLUB_Q);
        game.addPlayer(player2);
        game.addPlayer(player1);
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.of(game));
        PlayerView expectedPlayerView1 = new PlayerView();
        expectedPlayerView1.setName("player1");
        expectedPlayerView1.setTotalValueOfCards(23);
        PlayerView expectedPlayerView2 = new PlayerView();
        expectedPlayerView2.setName("player2");
        expectedPlayerView2.setTotalValueOfCards(19);

        List<PlayerView> playerViews = gameService.getPlayersInGame(GAME_NAME);

        assertEquals(2, playerViews.size());
        assertEquals(expectedPlayerView1, playerViews.get(0));
        assertEquals(expectedPlayerView2, playerViews.get(1));
    }


}