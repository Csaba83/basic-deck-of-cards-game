package com.bdocg.service;

import com.bdocg.domain.Card;
import com.bdocg.domain.Deck;
import com.bdocg.repository.DeckRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeckServiceTest {

    @Mock
    private DeckRepository mockDeckRepository;

    @InjectMocks
    private DeckService deckService;

    @Test
    public void createDeck() {
        Deck deck = new Deck("test", Arrays.asList(Card.values()));

        deckService.createDeck("test");

        verify(mockDeckRepository).save(deck);
    }
}