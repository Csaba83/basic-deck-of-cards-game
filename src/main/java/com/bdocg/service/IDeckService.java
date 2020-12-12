package com.bdocg.service;

import com.bdocg.domain.Deck;

public interface IDeckService {

    void createDeck(String name);

    Deck getDeck(String name);
}
