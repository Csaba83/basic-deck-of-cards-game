package com.bdocg.repository;

import com.bdocg.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findGameByName(String name);
}
