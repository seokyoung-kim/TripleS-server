package com.triples.project.scheduler.crawling.festa;

import com.triples.project.dao.collection.Card;
import com.triples.project.scheduler.crawling.ICrawling;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author : 장원용
 * @URL : https://festa.io
 * @description : URL에 있는 내용을 크롤링하는 클래스
 */

@Component
@RequiredArgsConstructor
public class FestaCrawling implements ICrawling {

    // webDriver bean 재사용
    private final WebDriver driver;
    private final WebDriverWait webDriverWait;

    private final String url = "https://festa.io/events";

    @Override
    public List<Card> startCrawling() throws InterruptedException {

        List<Card> cardList = new ArrayList<Card>();

        driver.get(url);

        JavascriptExecutor js = (JavascriptExecutor) driver;



        for(int i =0; i< 3; i++) {

            js.executeScript("window.scrollTo(0,document.body.scrollHeight)");

            //데이터가 로딩되는데 시간이 걸리기 때문에 로딩 시간을 기다려 줘야 함.
            synchronized (webDriverWait) {
                webDriverWait.wait(3000);
            }



        }



        List<WebElement> list = driver.findElements(By.xpath("//a[@class='EventCardMobile__Card-sc-1do1vt8-0 dHIdtb']"));

        for(WebElement target : list) {

            String link = target.getAttribute("href");
            String image = target.findElement(By.xpath(".//div[@src]")).getAttribute("src");
            String title = target.findElement(By.xpath(".//h3")).getAttribute("textContent");
            String data = target.findElement(By.xpath(".//time")).getAttribute("datetime"); // 크롤링 한 시간
            String writer = target.findElement(By.xpath(".//span")).getAttribute("textContent"); // 작성자

            String card_footer = target.findElement(By.xpath(".//div[@class='EventCardMobile__Fee-sc-1do1vt8-11 jpyylk']"))
                    .getAttribute("textContent");
            // 무료, 유료 교육 , 외부 이벤트 로 수정 할 것

            System.out.println(">>>>>>>>>>>>" + link);
            System.out.println(">>>>>>>>>>>>" + image);
            System.out.println(">>>>>>>>>>>>" + title);
            System.out.println(">>>>>>>>>>>>" + data);
            System.out.println(">>>>>>>>>>>>" + writer);
            System.out.println(">>>>>>>>>>>>" + card_footer);

            cardList.add(Card.builder().title(title).link(link).image(image)
                    .date(data).writer(writer).build());
        }

        return cardList;
    }
}
