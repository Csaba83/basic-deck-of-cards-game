package com.bdocg.service;

import com.bdocg.domain.Deck;
import com.bdocg.domain.Game;
import com.bdocg.domain.Player;
import com.bdocg.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService implements IGameService {

    private GameRepository gameRepository;

    private IDeckService deckService;

    private IPlayerService playerService;

    @Autowired
    public GameService(GameRepository gameRepository, IDeckService deckService, IPlayerService playerService) {
        this.gameRepository = gameRepository;
        this.deckService = deckService;
        this.playerService = playerService;
    }

    @Override
    public void createGame(String name) {
        gameRepository.save(new Game(name));
    }

    @Override
    public void deleteGame(String name) {
        gameRepository.findGameByName(name).ifPresent(game -> gameRepository.delete(game));
    }

    @Override
    public void addDeckToGame(String gameName, String deckName) {
        Game game = findGameByName(gameName);
        Deck deck = deckService.getDeck(deckName);
        game.addDeck(deck);
        gameRepository.save(game);
    }

    private Game findGameByName(String gameName) {
        Optional<Game> possibleGame = gameRepository.findGameByName(gameName);
        return possibleGame.orElseThrow(() -> new NotFoundException("Game is not found by the specified name"));
    }

    @Override
    public void addPlayerToGame(String gameName, String playerName) {
        Game game = findGameByName(gameName);
        game.addPlayer(playerService.createPlayer(playerName));
        gameRepository.save(game);
    }

    @Override
    public void removePlayerFromGame(String gameName, String playerName) {
        Game game = findGameByName(gameName);
        game.removePlayer(playerName);
        gameRepository.save(game);
    }

    @Override
    public void dealCardsToPlayer(String gameName, String playerName, int numberOfCards) {
        Game game = findGameByName(gameName);
        Player player = getPlayerFromGame(playerName, game);

        if (game.getShoeSize() >= numberOfCards) {
            player.addCard(game.popNextCardFromShoe());
            gameRepository.save(game);
        }
    }

    private Player getPlayerFromGame(String playerName, Game game) {
        return game.getPlayers()
                    .stream()
                    .filter(player1 -> player1.getName().equals(playerName))
                    .findAny()
                    .orElseThrow(() -> new NotFoundException("Player is not found by the specified name"));
    }
}
