package com.bdocg.domain;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "game")
    private List<Player> players = new ArrayList<>();

    @ElementCollection
    @Enumerated
    private List<Card> shoe = new LinkedList<>();

    public Game() {
    }

    public Game(String name) {
        this.name = name;
    }

    public void addDeck(Deck deck) {
        shoe.addAll(deck.getCards());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id &&
                name.equals(game.name) &&
                players.equals(game.players) &&
                shoe.equals(game.shoe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, players, shoe);
    }
}
