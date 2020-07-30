package com.triples.project.dao.brunch;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.triples.project.dao.ICardDao;
import com.triples.project.dao.collection.Card;

import lombok.RequiredArgsConstructor;

/**
 * @author qusrh
 * @description 스프링부트에서 impl 클래스는 interface와 이름을 맞춰야 한다. 물론 다르게 설정하는 방법은 찾으면 있겠지만 그냥 맞추자.
 */
@RequiredArgsConstructor
public class ICardDaoCustomImpl implements ICardDaoCustom{

	private final MongoTemplate mongoTemplate;
	private final ICardDao cardDao;
	
	@Override
	public void mergeCard(List<Card> newCardList) {
		List<Card> oldCardList = selectBrunchCardList();
		
		for(Card oldCard : oldCardList) {
			String oldTitle    = oldCard.getTitle();
			String oldCategory = oldCard.getCategory();
			String oldWriter   = oldCard.getWriter();
			String oldPlatform = oldCard.getPlatform();
			
			for(Card newCard : newCardList) {
				String newTitle    = newCard.getTitle();
				String newCategory = newCard.getCategory();
				String newWriter   = newCard.getWriter();
				String newPlatform = newCard.getPlatform();

				if(oldTitle.equals(newTitle) && oldCategory.equals(newCategory) && oldWriter.equals(newWriter) && oldPlatform.equals(newPlatform)) {
					
				}
				else {
					
				}
			}
		}
	}
	
	private List<Card> selectBrunchCardList(){
		Query query = new Query();
		return mongoTemplate.find(query, Card.class);
	}
	
	private void updateBrunchCard(String title) {
		Update update = new Update();
		update.set("title", title);
		
		Query query = new Query();
		
		mongoTemplate.updateFirst(query, update, Card.class);
	}
}
