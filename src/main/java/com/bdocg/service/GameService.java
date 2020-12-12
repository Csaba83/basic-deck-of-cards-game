package com.bdocg.service;

import com.bdocg.domain.Game;
import com.bdocg.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService implements IGameService {

    private GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
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
}
