package com.bdocg.repository;

import com.bdocg.domain.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDeckRepository extends JpaRepository<Deck, Long> {

    Optional<Deck> findDeckByName(String name);
}
