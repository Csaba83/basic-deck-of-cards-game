package com.bdocg.controller;

import com.bdocg.view.CardSuitCountView;
import com.bdocg.view.CardView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@RequestMapping("/player")
public interface IPlayerController {

    @PostMapping(value = "{name}")
    ResponseEntity<Void> createPlayer(@PathVariable(value = "name") String name);

    @GetMapping(value = "/{name}/getCards")
    ResponseEntity<Collection<CardView>> getPlayerCards(@PathVariable(value = "name") String name);
}
