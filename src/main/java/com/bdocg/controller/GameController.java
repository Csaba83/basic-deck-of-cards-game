package com.bdocg.controller;

import com.bdocg.service.IGameService;
import com.bdocg.view.CardCountView;
import com.bdocg.view.CardSuitCountView;
import com.bdocg.view.PlayerView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class GameController implements IGameController {

    private IGameService gameService;

    @Autowired
    public GameController(IGameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public ResponseEntity<Void> createGame(String name) {
        gameService.createGame(name);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> deleteGame(String name) {
        gameService.deleteGame(name);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> addDeckToGame(String gameName, String deckName) {
        gameService.addDeckToGame(gameName, deckName);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> addPlayerToGame(String gameName, String playerName) {
        gameService.addPlayerToGame(gameName, playerName);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> removePlayerFromGame(String gameName, String playerName) {
        gameService.removePlayerFromGame(gameName, playerName);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> dealCardsToPlayer(String gameName, String playerName, int numberOfCards) {
        gameService.dealCardsToPlayer(gameName, playerName, numberOfCards);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Collection<PlayerView>> getPlayers(String gameName) {
        return ResponseEntity.ok(gameService.getPlayersInGame(gameName));
    }

    @Override
    public ResponseEntity<Collection<CardSuitCountView>> getCountOfUndealtCardsPerSuit(String gameName) {
        return ResponseEntity.ok(gameService.getCountOfUndealtCardsPerSuit(gameName));
    }

    @Override
    public ResponseEntity<Collection<CardCountView>> getCountOfUndealtCards(String gameName) {
        return ResponseEntity.ok(gameService.getCountOfUndealtCards(gameName));
    }

    @Override
    public ResponseEntity<Void> shuffleShoe(String gameName) {
        gameService.shuffleShoe(gameName);
        return ResponseEntity.ok().build();
    }
}
