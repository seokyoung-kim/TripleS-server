package com.triples.project.service;

import com.triples.project.dao.ICardDao;
import com.triples.project.dao.collection.Card;
import lombok.RequiredArgsConstructor;
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
}
