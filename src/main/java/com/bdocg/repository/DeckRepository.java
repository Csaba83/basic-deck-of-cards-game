package com.bdocg.repository;

import com.bdocg.domain.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeckRepository extends JpaRepository<Deck, Long> {

    Optional<Deck> findDeckByName(String name);
}
