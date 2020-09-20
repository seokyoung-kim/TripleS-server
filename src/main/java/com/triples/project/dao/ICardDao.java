package com.triples.project.dao;

import com.triples.project.dao.collection.Card;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ICardDao extends MongoRepository<Card, ObjectId> {

    // first page request
    List<Card> findAllByOrderByIdDesc(Pageable pageable);

    // next cursor pagination after first request
    List<Card> findByIdLessThanOrderByIdDesc(ObjectId id, Pageable pageable);

    // prev cursor pagination after first request
    List<Card> findByIdGreaterThanOrderByIdAsc(ObjectId id, Pageable pageable);

    // whether next cursor exists
    Boolean existsByIdLessThanOrderByIdDesc(ObjectId id);

    // whether prev cursor exists
    Boolean existsByIdGreaterThanOrderByIdDesc(ObjectId id);

    List<Card> findByPlatform(String platform);

    List<Card> findByWriter(String writer);

    List<Card> findByCategory(String category);

    List<Card> findByLink(String link);
}
