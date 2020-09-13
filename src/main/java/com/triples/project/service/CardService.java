package com.triples.project.service;

import com.triples.project.dao.ICardDao;
import com.triples.project.dao.collection.Card;
import com.triples.project.dto.CursorResult;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public CursorResult<Card> findAllByOrderByIdDesc(ObjectId cursorId, Pageable page) {
        List<Card> cards = getCards(cursorId, page);
        ObjectId lastCursorId = cards.isEmpty() ? null :
                                cards.get(cards.size()-1).getId();

        return new CursorResult<>(cards,hasNext(lastCursorId),lastCursorId, cards.size());
    }

    private List<Card> getCards(ObjectId id, Pageable page) {
        return id == null ?
                iCardDao.findAllByOrderByIdDesc(page) :
                iCardDao.findByIdLessThanOrderByIdDesc(id, page);
    }
    private Boolean hasNext(ObjectId id) {
        if(id == null) return false;
        return iCardDao.existsByIdLessThanOrderByIdDesc(id);
    }
}
