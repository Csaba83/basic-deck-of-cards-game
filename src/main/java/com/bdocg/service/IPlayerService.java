package com.bdocg.service;

import com.bdocg.domain.Player;
import com.bdocg.view.CardView;

import java.util.Collection;

public interface IPlayerService {

    void createPlayer(String name);

    Player getPlayer(String name);

    Collection<CardView> getPlayerCards(String name);
}
