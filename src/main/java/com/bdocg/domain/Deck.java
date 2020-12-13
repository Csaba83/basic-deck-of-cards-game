package com.bdocg.domain;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ElementCollection
    @Enumerated
    private List<Card> cards = new ArrayList<>();

    public Deck() {
    }

    public Deck(String name, List<Card> cards) {
        this.name = name;
        this.cards = cards;
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return id == deck.id &&
                name.equals(deck.name) &&
                cards.equals(deck.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cards);
    }
}
