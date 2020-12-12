package com.bdocg.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deck")
public interface IDeckController {

    @PostMapping(value = "/{name}")
    ResponseEntity<Void> createDeck(@PathVariable(value = "name") String name);
}
