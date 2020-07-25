package com.triples.project.dao.brunch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import com.triples.project.dao.collection.Card;

public class CardDaoImpl implements ICardDaoCustom{

	private final MongoTemplate mongoTemplate;
	
	@Autowired
	public CardDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
	
	@Override
	public void mergeCard(List<Card> cardList) {
		List<AggregationOperation> list = new ArrayList<AggregationOperation>();
	}
	
}
