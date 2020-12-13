package com.bdocg.service;

import com.bdocg.domain.Card;
import com.bdocg.domain.Deck;
import com.bdocg.repository.IDeckRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeckServiceTest {

    private static final String DECK_NAME = "testDeckName";
    @Mock
    private IDeckRepository mockDeckRepository;

    @InjectMocks
    private DeckService deckService;

    @Test
    public void createDeck() {
        Deck deck = new Deck(DECK_NAME, Arrays.asList(Card.values()));

        deckService.createDeck(DECK_NAME);

        verify(mockDeckRepository).save(deck);
    }

    @Test
    public void getDeck() {
        Deck deck = new Deck(DECK_NAME, Arrays.asList(Card.values()));
        when(mockDeckRepository.findDeckByName(DECK_NAME)).thenReturn(Optional.of(deck));

        Deck actualDeck = deckService.getDeck(DECK_NAME);

        Assert.assertEquals(deck, actualDeck);
    }

    @Test(expected = NotFoundException.class)
    public void getNonExistentDeckThrowsException() {
        when(mockDeckRepository.findDeckByName(DECK_NAME)).thenReturn(Optional.empty());

        deckService.getDeck(DECK_NAME);
    }
}