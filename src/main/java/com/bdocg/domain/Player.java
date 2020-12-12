package com.bdocg.domain;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint( columnNames = {"game_id", "name"})})
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    private String name;

    @ElementCollection
    @Enumerated
    private List<Card> cards;

    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }
}
