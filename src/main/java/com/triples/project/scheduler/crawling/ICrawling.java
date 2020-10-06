package com.triples.project.scheduler.crawling;

import java.util.List;

import com.triples.project.dao.collection.Card;

public interface ICrawling {

    //드라이버를 인자로 받아 크롤링하는 데이터에 맞는 로직을 구현하는 메서드
    public List<Card> startCrawling();
}
