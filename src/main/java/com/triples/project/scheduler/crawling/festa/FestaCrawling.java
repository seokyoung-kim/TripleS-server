package com.triples.project.scheduler.crawling.festa;

import com.triples.project.dao.collection.Card;
import com.triples.project.scheduler.crawling.ICrawling;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * @author : 장원용
 * @URL : https://festa.io
 * @description : URL에 있는 내용을 크롤링하는 클래스
 */

@Component
@RequiredArgsConstructor
public class FestaCrawling implements ICrawling {

    // driver 재사용
    private final WebDriver driver;

    @Override
    public List<Card> startCrawling() {

        List<Card> cardList = new ArrayList<>();

        String url = "https://festa.io/";
        driver.get(url);

        List<WebElement> list = driver.findElements(By.xpath("//a[@class='EventCardMobile__Card-sc-1do1vt8-0 dHIdtb']"));

        for(WebElement target : list) {

            String link = target.findElement(By.xpath(".//div[@src]")).getAttribute("src");
            String title = target.findElement(By.xpath(".//h3")).getAttribute("textContent");
            String image = target.getAttribute("href");

            cardList.add(Card.builder().title(title).link(link).image(image).build());
        }

        return cardList;
    }
}
