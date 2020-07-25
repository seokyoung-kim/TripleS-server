package com.triples.project.controller;

import com.triples.project.dao.collection.Card;
import com.triples.project.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class CardApiController {

    private final CardService cardService;

    /**
     * 전체 카드 리스트 Find
     */
    @GetMapping("/api/v1/cards")
    public ResponseEntity<List<Card>> findAll() {

        List<Card> cardList = cardService.findAll();

        return ResponseEntity.ok().body(cardList);
    }
}
