package com.bdocg.service;

import com.bdocg.domain.Card;
import com.bdocg.domain.Deck;
import com.bdocg.domain.Game;
import com.bdocg.domain.Player;
import com.bdocg.repository.GameRepository;
import com.bdocg.view.CardCountView;
import com.bdocg.view.CardSuitCountView;
import com.bdocg.view.PlayerView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        gameToSave.setPlayers(Collections.emptyList());
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.of(game));

        gameService.removePlayerFromGame(GAME_NAME, PLAYER_NAME);

        verify(mockGameRepository).save(gameToSave);
    }

    @Test(expected = NotFoundException.class)
    public void removePlayerFromNonExistentGame() {
        gameToSave.setPlayers(Collections.emptyList());
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.empty());

        gameService.addPlayerToGame(GAME_NAME, PLAYER_NAME);
    }

    @Test
    public void dealOneCardToPlayer() {
        game.addDeck(deck);
        game.addPlayer(playerWithNoCard);
        gameToSave.addDeck(emptyDeck);
        gameToSave.addPlayer(playerWithOneCard);
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.of(game));

        gameService.dealCardsToPlayer(GAME_NAME, PLAYER_NAME, 1);

        verify(mockGameRepository).save(gameToSave);
    }

    @Test
    public void dealMultipleCardToPlayer() {
        //TODO: cleanup
        deck = new Deck(DECK_NAME, Arrays.asList(Card.CLUB_A, Card.CLUB_2));
        game.addDeck(deck);
        game.addPlayer(playerWithNoCard);
        gameToSave.addDeck(emptyDeck);
        gameToSave.addPlayer(playerWithOneCard);
        playerWithOneCard.addCard(Card.CLUB_2);
        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.of(game));

        gameService.dealCardsToPlayer(GAME_NAME, PLAYER_NAME, 2);

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

    @Test
    public void getCountOfUndealtCardsPerSuit() {
        Deck deck = new Deck(DECK_NAME, Arrays.asList(
                Card.CLUB_A,
                Card.CLUB_7,
                Card.HEART_A
        ));
        game.addDeck(deck);

        Set<CardSuitCountView> expectedCardSuitCountViews = new HashSet<>();
        CardSuitCountView expectedCardSuitCountView1 = new CardSuitCountView();
        expectedCardSuitCountView1.setSuit("Club");
        expectedCardSuitCountView1.setCount(2);
        expectedCardSuitCountViews.add(expectedCardSuitCountView1);
        CardSuitCountView expectedCardSuitCountView2 = new CardSuitCountView();
        expectedCardSuitCountView2.setSuit("Heart");
        expectedCardSuitCountView2.setCount(1);
        expectedCardSuitCountViews.add(expectedCardSuitCountView2);

        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.of(game));

        Set<CardSuitCountView> undealtCardsCountViews = gameService.getCountOfUndealtCardsPerSuit(GAME_NAME);
        assertEquals(2, undealtCardsCountViews.size());
        assertEquals(expectedCardSuitCountViews, undealtCardsCountViews);
    }

    @Test
    public void getCountOfUndealtCards() {
        Deck deck = new Deck(DECK_NAME, Arrays.asList(
                Card.CLUB_A,
                Card.CLUB_7,
                Card.CLUB_7,
                Card.HEART_K
        ));
        game.addDeck(deck);

        List<CardCountView> expectedCardCountViews = new ArrayList<>();
        CardCountView expectedCardCountView2 = new CardCountView();
        expectedCardCountView2.setSuit("Club");
        expectedCardCountView2.setValue("7");
        expectedCardCountView2.setCount(2);
        CardCountView expectedCardCountView1 = new CardCountView();
        expectedCardCountView1.setSuit("Club");
        expectedCardCountView1.setValue("A");
        expectedCardCountView1.setCount(1);
        CardCountView expectedCardCountView3 = new CardCountView();
        expectedCardCountView3.setSuit("Heart");
        expectedCardCountView3.setValue("K");
        expectedCardCountView3.setCount(1);

        expectedCardCountViews.add(expectedCardCountView2);
        expectedCardCountViews.add(expectedCardCountView1);
        expectedCardCountViews.add(expectedCardCountView3);

        when(mockGameRepository.findGameByName(GAME_NAME)).thenReturn(Optional.of(game));

        List<CardCountView> undealtCardsCountViews = gameService.getCountOfUndealtCards(GAME_NAME);
        assertEquals(3, undealtCardsCountViews.size());
        assertEquals(expectedCardCountView2, undealtCardsCountViews.get(0));
        assertEquals(expectedCardCountView1, undealtCardsCountViews.get(1));
        assertEquals(expectedCardCountView3, undealtCardsCountViews.get(2));
        assertEquals(expectedCardCountViews, undealtCardsCountViews);
    }

}