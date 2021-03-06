package com.bdocg.repository;

import com.bdocg.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IGameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findGameByName(String name);
}
