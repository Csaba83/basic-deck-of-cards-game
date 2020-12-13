package com.bdocg.repository;

import com.bdocg.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findPlayerByName(String name);
}
