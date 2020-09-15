package com.triples.project.service;

import com.triples.project.dao.ICardDao;
import com.triples.project.dao.collection.Card;
import com.triples.project.dto.Cursor;
import com.triples.project.dto.CursorResult;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CardService {

    private final ICardDao iCardDao;

    // 조회 기능만 있을 경우 readOnly 조회 속도 개선
    @Transactional(readOnly = true)
    public List<Card> findAll() {
        return iCardDao.findAll();
    }

    @Transactional(readOnly = true)
    public List<Card> findByPlatform(String platform) {
        return iCardDao.findByPlatform(platform);
    }

    @Transactional(readOnly = true)
    public List<Card> findByWriter(String writer) {
        return iCardDao.findByWriter(writer);
    }

    @Transactional(readOnly = true)
    public List<Card> findByCategory(String category) {
        return iCardDao.findByCategory(category);
    }

    @Transactional
    public CursorResult<Card> findAllByOrderByIdDesc(String inputPrev, String inputNext, Pageable page) {

        ObjectId cursorId = null;

        if(inputPrev == null) {       // next cursor
            cursorId =
                    inputNext != null ? new ObjectId(inputNext) : null;

            return nextCards(cursorId, page);
        }
        else {                       // prev cursor
            cursorId = new ObjectId(inputPrev);

            return prevCards(cursorId, page);
        }
    }
    private CursorResult<Card> prevCards(ObjectId cursorId, Pageable page) {
        ObjectId nextId = null, prevId = null;
        String next = null, previous = null;
        List<Card> cards = null;

        cards = iCardDao.findByIdGreaterThanOrderByIdAsc(cursorId, page);
        if(cards != null ) Collections.reverse(cards);

        prevId = findFirstObjectId(cards);                          // Previous String cursor
        previous = prevId != null ? prevId.toHexString() : null;   // Previous Object cursor

        nextId = findLastObjectId(cards);
        next = nextId != null ? nextId.toHexString() : null;

        return new CursorResult<>(cards, Cursor.builder()
                .hasPrevious(checkPrev(prevId))
                .previous(previous)
                .next(next)
                .hasNext(checkNext(nextId))
                .build());
    }

    private CursorResult<Card> nextCards(ObjectId cursorId, Pageable page) {

        ObjectId prevId = null, nextId = null;
        String previous = null, next = null;
        List<Card> cards = null;

        if(cursorId == null) { // first request
            cards = iCardDao.findAllByOrderByIdDesc(page);
        }
        else {                 // after first request
            cards = iCardDao.findByIdLessThanOrderByIdDesc(cursorId, page);
        }

        prevId = findFirstObjectId(cards);                        // Prev Object cursor
        previous = prevId != null ? prevId.toHexString() : null; // Prev String cursor

        nextId = findLastObjectId(cards);                      // Next Object cursor
        next = nextId != null ? nextId.toHexString() : null;  // Next String cursor

        return new CursorResult<>(cards, Cursor.builder()
                .hasPrevious(checkPrev(prevId))
                .previous(previous)
                .next(next)
                .hasNext(checkNext(nextId))
                .build());
    }

    private Boolean checkPrev(ObjectId id) {
        if(id == null) return false;
        return iCardDao.existsByIdGreaterThanOrderByIdDesc(id);
    }
    private Boolean checkNext(ObjectId id) {
        if(id == null) return false;
        return iCardDao.existsByIdLessThanOrderByIdDesc(id);
    }
    private ObjectId findLastObjectId(List<Card> cards) {
        if(cards.isEmpty()) return null;
        else return cards.get(cards.size()-1).getId();
    }
    private ObjectId findFirstObjectId(List<Card> cards) {
        if(cards.isEmpty()) return null;
        else return cards.get(0).getId();
    }
}
