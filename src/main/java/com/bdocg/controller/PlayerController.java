package com.bdocg.controller;

import com.bdocg.service.IPlayerService;
import com.bdocg.view.CardView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class PlayerController implements IPlayerController {

    private IPlayerService playerService;

    @Autowired
    public PlayerController(IPlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public ResponseEntity<Void> createPlayer(String name) {
        playerService.createPlayer(name);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Collection<CardView>> getPlayerCards(String name) {
        return ResponseEntity.ok(playerService.getPlayerCards(name));
    }
}
