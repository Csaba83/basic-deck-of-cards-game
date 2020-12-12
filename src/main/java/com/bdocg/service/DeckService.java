package com.bdocg.service;

import com.bdocg.domain.Card;
import com.bdocg.domain.Deck;
import com.bdocg.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DeckService implements IDeckService {

    private DeckRepository deckRepository;

    @Autowired
    public DeckService(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    @Override
    public void createDeck(String name) {
        Deck deck = new Deck(name, Arrays.asList(Card.values()));
        deckRepository.save(deck);
    }

    @Override
    public Deck getDeck(String name) {
        return deckRepository.findDeckByName(name).orElseThrow(() -> new NotFoundException("Deck is not found by the specified name"));
    }
}
