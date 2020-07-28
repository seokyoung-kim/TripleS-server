package com.triples.project.dao.brunch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.MergeOperation;
import org.springframework.data.mongodb.core.aggregation.MergeOperation.MergeOperationBuilder;
import org.springframework.data.mongodb.core.aggregation.MergeOperation.MergeOperationTarget;
import org.springframework.data.mongodb.core.aggregation.MergeOperation.WhenDocumentsDontMatch;
import org.springframework.data.mongodb.core.aggregation.MergeOperation.WhenDocumentsMatch;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.aggregation.MergeOperation.MergeOperationTarget;

import com.triples.project.dao.collection.Card;

import lombok.RequiredArgsConstructor;

/**
 * @author qusrh
 * @description 스프링부트에서 impl 클래스는 interface와 이름을 맞춰야 한다. 물론 다르게 설정하는 방법은 찾으면 있겠지만 그냥 맞추자.
 */
@RequiredArgsConstructor
public class ICardDaoCustomImpl implements ICardDaoCustom{

	private final MongoTemplate mongoTemplate;
	
	@Override
	public List<Card> mergeCard(List<Card> cardList) {
//		MergeOperationBuilder merge = new MergeOperationBuilder();
//		MergeOperationTarget mergeTarget = MergeOperationTarget.collection("card");
//		merge.into(mergeTarget);
//		String[] colum = new String[3];
//		colum[0] = "id";
//		colum[1] = "title";
//		colum[2] = "description";
//		merge.on(colum);
//		merge.whenMatched(WhenDocumentsMatch.replaceDocument());
//		merge.whenNotMatched(WhenDocumentsDontMatch.insertNewDocument());
//
//		MergeOperation mergeOperation = merge.build();
//		
//		mongoTemplate.aggregate(Aggregation.newAggregation(mergeOperation), Card.class, Card.class);
		
		Criteria criteria = new Criteria().where("card");
		Query query = new Query(criteria);
		return mongoTemplate.find(query, Card.class);
	}
	
}
