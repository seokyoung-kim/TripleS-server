package com.triples.project.dao;

import com.triples.project.dao.collection.Card;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ICardDao extends MongoRepository<Card, ObjectId> {

    List<Card> findAllByOrderByIdDesc(Pageable pageable);

    List<Card> findByIdLessThanOrderByIdDesc(ObjectId id, Pageable pageable);

    Boolean existsByIdLessThanOrderByIdDesc(ObjectId id);

    Boolean existsIdBy(ObjectId id);

    List<Card> findByPlatform(String platform);

    List<Card> findByWriter(String writer);

    List<Card> findByCategory(String category);
}
