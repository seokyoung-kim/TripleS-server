package com.triples.project.dao.brunch;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.triples.project.dao.ICardDao;
import com.triples.project.dao.collection.Card;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CardDaoCustomTest {
	
	@Autowired
	public ICardDao cardDao;
	
	@Test
	public void test() {
		List<Card> cardList = new ArrayList<Card>();
		List<Card> resultList2 = cardDao.findAll();
		
//		System.out.println("길이");
//		System.out.println(resultList.size());
//		for(Card card : resultList) {
//			System.out.println(card.getTitle());
//		}
		
		System.out.println("길이");
		System.out.println(resultList2.size());
		for(Card card : resultList2) {
			System.out.println(card.getTitle());
		}
	}
}
