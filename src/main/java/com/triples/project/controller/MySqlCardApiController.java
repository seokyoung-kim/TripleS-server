package com.triples.project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triples.project.domain.Card;
import com.triples.project.domain.repository.CardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@RestController
public class MySqlCardApiController {

    private final CardRepository cardRepository;

    /**
     * mysql sample data
     */
    @GetMapping("/api/v1/mysql/cards")
    public ResponseEntity<List<Card>> findAll() {

        log.info("mysql findAll");

        List<Card> cardList = cardRepository.findAll();
        return ResponseEntity.ok().body(cardList);
    }
}
