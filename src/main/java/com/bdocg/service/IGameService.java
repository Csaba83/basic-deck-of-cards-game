package com.bdocg.service;

public interface IGameService {

    void createGame(String name);

    void deleteGame(String name);

    void addDeckToGame(String gameName, String deckName);

    void addPlayerToGame(String gameName, String playerName);

    void removePlayerFromGame(String gameName, String playerName);

    void dealCardsToPlayer(String gameName, String playerName, int numberOfCards);
}
