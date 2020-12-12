package com.bdocg.service;

import com.bdocg.domain.Deck;
import com.bdocg.domain.Game;
import com.bdocg.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService implements IGameService {

    private GameRepository gameRepository;

    private IDeckService deckService;

    @Autowired
    public GameService(GameRepository gameRepository, IDeckService deckService) {
        this.gameRepository = gameRepository;
        this.deckService = deckService;
    }

    @Override
    public void createGame(String name) {
        gameRepository.save(new Game(name));
    }

    @Override
    public void deleteGame(String name) {
        Optional<Game> possibleGame = gameRepository.findGameByName(name);
        possibleGame.ifPresent(game -> gameRepository.delete(game));
    }

    @Override
    public void addDeckToGame(String gameName, String deckName) {
        Optional<Game> possibleGame = gameRepository.findGameByName(gameName);
        Game game = possibleGame.orElseThrow(() -> new NotFoundException("Game is not found by the specified name"));
        Deck deck = deckService.getDeck(deckName);
        game.addDeck(deck);
        gameRepository.save(game);
    }
}
