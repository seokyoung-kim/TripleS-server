package com.triples.project.service;

import com.triples.project.dao.collection.Card;
import com.triples.project.dto.CursorResult;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CardServiceTest {

    @Autowired
    private CardService cardService;

    @Test
    @DisplayName("커서 페이지네이션 테스트")
    public void cursorBasedPagination() {

        // first request
        CursorResult<Card> cards = cardService.findAllByOrderByIdDesc(null, PageRequest.of(0,10));

        ObjectId nextCursorId = cards.getCursorId();
        List<Card> list = cards.getValues();
        assertThat(nextCursorId).isEqualTo(list.get(list.size()-1).getId());

        // after first request
        cards = cardService.findAllByOrderByIdDesc(nextCursorId, PageRequest.of(0,10));
        nextCursorId = cards.getCursorId();
        list = cards.getValues();
        assertThat(nextCursorId).isEqualTo(list.get(list.size()-1).getId());

    }
}