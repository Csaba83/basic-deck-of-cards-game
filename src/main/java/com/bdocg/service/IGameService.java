package com.bdocg.service;

import com.bdocg.view.CardSuitCountView;
import com.bdocg.view.PlayerView;

import java.util.Collection;
import java.util.List;

public interface IGameService {

    void createGame(String name);

    void deleteGame(String name);

    void addDeckToGame(String gameName, String deckName);

    void addPlayerToGame(String gameName, String playerName);

    void removePlayerFromGame(String gameName, String playerName);

    void dealCardsToPlayer(String gameName, String playerName, int numberOfCards);

    Collection<PlayerView> getPlayersInGame(String gameName);

    Collection<CardSuitCountView> getCountOfUndealtCardsPerSuit(String gameName);
}
