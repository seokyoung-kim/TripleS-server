package com.triples.project.dao;

import com.triples.project.dao.collection.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface ICardDao extends MongoRepository<Card, String> {


    List<Card> findByPlatform(String platform);

    List<Card> findByWriter(String writer);

    List<Card> findByCategory(String category);
}
