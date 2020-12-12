package com.bdocg.controller;

import com.bdocg.service.IDeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeckController implements IDeckController {

    private IDeckService deckService;

    @Autowired
    public DeckController(IDeckService deckService) {
        this.deckService = deckService;
    }

    @Override
    public ResponseEntity<Void> createDeck(String name) {
        deckService.createDeck(name);
        return ResponseEntity.ok().build();
    }
}
