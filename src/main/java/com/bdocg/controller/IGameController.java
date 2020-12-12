package com.bdocg.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bdocg.view.CardCountView;
import com.bdocg.view.CardView;
import com.bdocg.view.PlayerView;

import java.util.List;

@RequestMapping("/game")
public interface IGameController {

    @PostMapping(value = "/{name}")
    ResponseEntity<Void> createGame(@PathVariable(value = "name") String name);

    @DeleteMapping(value = "/{name}")
    ResponseEntity<Void> deleteGame(@PathVariable(value = "name") String name);

    @PostMapping(value = "/{gameName}/deck/{deckName}")
    ResponseEntity<Void> addDeckToGame(@PathVariable(value = "gameName") String gameName,
                                       @PathVariable(value = "deckName") String deckName);

    @PostMapping(value = "/{gameName}/player/{playerName}")
    ResponseEntity<Void> addPlayerToGame(@PathVariable(value = "gameName") String gameName,
                                         @PathVariable(value = "playerName") String playerName);

    @DeleteMapping(value = "/{gameName}/player/{playerName}")
    ResponseEntity<Void> removePlayerFromGame(@PathVariable(value = "gameName") String gameName,
                                              @PathVariable(value = "playerName") String playerName);

    @PostMapping(value = "/{gameName}/player/{playerName}/dealCards/{numberOfCards}")
    ResponseEntity<Void> dealCardsToPlayer(@PathVariable(value = "gameName") String gameName,
                                           @PathVariable(value = "playerName") String playerName,
                                           @PathVariable(value = "numberOfCards") int numberOfCards);

    @GetMapping(value = "/{gameName}/player/{playerName}/getCards")
    ResponseEntity<List<CardView>> getPlayerCards(@PathVariable(value = "gameName") String gameName,
                                                  @PathVariable(value = "playerName") String playerName);

    @GetMapping(value = "/{gameName}/players")
    ResponseEntity<List<PlayerView>> getPlayers(@PathVariable(value = "gameName") String gameName);

    @GetMapping(value = "/{gameName}/undealtCards")
    ResponseEntity<List<CardView>> getUndealtCards(@PathVariable(value = "gameName") String gameName);

    @GetMapping(value = "/{gameName}/countOfUndealtCards")
    ResponseEntity<List<CardCountView>> getCountOfUndealtCardsSorted(@PathVariable(value = "gameName") String gameName);

    @PatchMapping(value = "/{gameName}/shuffleShoe")
    ResponseEntity<Void> shuffleShoe(@PathVariable(value = "gameName") String gameName);
}
