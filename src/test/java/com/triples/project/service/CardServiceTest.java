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

        // first request ( next )
        CursorResult<Card> cards = cardService.findAllByOrderByIdDesc(null, null, PageRequest.of(0,10));

        String nextCursorId = cards.getCursors().getNext();
        List<Card> list = cards.getValues();
        String target = list.get(list.size()-1).getId().toHexString();
        assertThat(nextCursorId).isEqualTo(target);

        // first request ( previous )
        String prevCursorId = list.get(0).getId().toHexString();
        cards = cardService.findAllByOrderByIdDesc(prevCursorId, null, PageRequest.of(0,10));
        List<Card> result = cards.getValues();
        assertThat(result.size()).isEqualTo(0);

        // after first request ( next )
        cards = cardService.findAllByOrderByIdDesc(null, nextCursorId, PageRequest.of(0,10));
        nextCursorId = cards.getCursors().getNext();
        list = cards.getValues();
        target = list.get(list.size()-1).getId().toHexString();
        assertThat(nextCursorId).isEqualTo(target);

        // after first request ( previous )
        String curPrevId = list.get(0).getId().toHexString();
        cards = cardService.findAllByOrderByIdDesc(curPrevId, null, PageRequest.of(0,10));
        list = cards.getValues();
        curPrevId = list.get(0).getId().toHexString();
        assertThat(curPrevId).isEqualTo(prevCursorId);
    }
}