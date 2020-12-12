package com.bdocg.service;

import com.bdocg.domain.Player;
import org.springframework.stereotype.Service;

@Service
public class PlayerService implements IPlayerService {
    @Override
    public Player createPlayer(String name) {
        return new Player(name);
    }
}
