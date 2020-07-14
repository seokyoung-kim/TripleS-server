package com.triples.project.dao;

import com.triples.project.dao.collection.Card;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ICardDao extends MongoRepository<Card, String> {
}
