package com.triples.project.controller;

import com.triples.project.dao.collection.Card;
import com.triples.project.dto.CursorResult;
import com.triples.project.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
public class CardApiController {

    private final CardService cardService;
    private static final int PAGE_SIZE = 20;

    /**
     * 전체 카드 리스트 불러오기
     */
    @GetMapping("/api/v1/cards")
    public ResponseEntity<CursorResult<Card>> findAll(@RequestParam(required = false) String next,
                                                      @RequestParam(required = false) String previous,
                                                      @RequestParam(required = false) Integer pageSize) {

        log.info("[findAll] previous cursor :" + previous);
        log.info("[findAll] next cursor :" + next);

        if(pageSize == null) pageSize = PAGE_SIZE;

        CursorResult<Card> result  =
                cardService.findAllByOrderByIdDesc(previous, next, PageRequest.of(0, pageSize));

        return ResponseEntity.ok().body(result);
    }

    /**
     * 해당 플랫폼 카드 불러오기
     */
    @GetMapping("/api/v1/cards/platform/{platform}")
    public ResponseEntity<List<Card>> findByPlatform(@PathVariable String platform) {

        log.info("findByPlatform : " + platform);

        List<Card> cardList = cardService.findByPlatform(platform);
        return ResponseEntity.ok().body(cardList);
    }

    /**
     * 해당 작성자 카드 불러오기
     */
    @GetMapping("/api/v1/cards/writer/{writer}")
    public ResponseEntity<List<Card>> findByWriter(@PathVariable String writer) {

        log.info("findByWriter : " + writer);

        List<Card> cardList = cardService.findByWriter(writer);
        return ResponseEntity.ok().body(cardList);
    }

    /**
     * 해당 카테고리 카드 불러오기
     */
    @GetMapping("/api/v1/cards/category/{category}")
    public ResponseEntity<List<Card>> findByCategory(@PathVariable String category) {

        log.info("findByCategory : " + category);

        List<Card> cardList = cardService.findByCategory(category);
        return ResponseEntity.ok().body(cardList);
    }
}
