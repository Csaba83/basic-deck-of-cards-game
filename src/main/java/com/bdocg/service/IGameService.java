package com.bdocg.service;

public interface IGameService {

    void createGame(String name);

    void deleteGame(String name);

    void addDeckToGame(String gameName, String deckName);
}
