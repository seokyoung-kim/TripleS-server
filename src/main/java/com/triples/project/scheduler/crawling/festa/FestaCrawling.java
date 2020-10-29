package com.triples.project.scheduler.crawling.festa;

import com.triples.project.dao.ICardDao;
import com.triples.project.dao.collection.Card;
import com.triples.project.scheduler.crawling.ICrawling;
import com.triples.project.util.ImageSizeUtil;
import com.triples.project.util.ImageToBase64Util;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * @author : 장원용
 * @URL : https://festa.io
 * @description : URL에 있는 내용을 크롤링하는 클래스
 */

@Component("festaCrawling")
@RequiredArgsConstructor
public class FestaCrawling implements ICrawling {

    // webDriver bean 재사용
    private final WebDriver driver;
    private final WebDriverWait webDriverWait;
    private final ICardDao iCardDao;

    private final String url = "https://festa.io/events";
    private final String platform = "festa";

    @SneakyThrows
    @Override
    public List<Card> startCrawling() {

        List<Card> cardList = new ArrayList<Card>();

        driver.get(url);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 크롤링 시작전에 데이터 로딩 Wait
        synchronized (webDriverWait) {
                webDriverWait.wait(3000);
            }

        for(int i =0; i< 3; i++) { // 각 url 에 따라 설정

            js.executeScript("window.scrollTo(0,document.body.scrollHeight)");

            // Scroll Down 후 데이터 로딩 Wait
            synchronized (webDriverWait) {
                webDriverWait.wait(3000);
            }
        }

        List<WebElement> list = driver.findElements(By.xpath("//a[@class='EventCardMobile__Card-sc-1do1vt8-0 dHIdtb']"));

        for(WebElement target : list) {

            String link = target.getAttribute("href");
            String image = ImageToBase64Util.getBase64String(target.findElement(By.xpath(".//div[@src]")).getAttribute("src"));
            String title = target.findElement(By.xpath(".//h3")).getAttribute("textContent");
            String date = target.findElement(By.xpath(".//time")).getAttribute("datetime");      // 작성 시간
            String writer = target.findElement(By.xpath(".//span")).getAttribute("textContent"); // 작성자
            String image_type   = ImageSizeUtil.getImageType(image);

//            String card_footer = target.findElement(By.xpath(".//div[@class='EventCardMobile__Fee-sc-1do1vt8-11 jpyylk']"))
//                    .getAttribute("textContent");
//            // 무료, 유료 교육 , 외부 이벤트 로 수정 할 것

            // delete duplication
            List<Card> cards = iCardDao.findByLink(link);
            if(cards.size() > 0) continue;

            cardList.add(Card.builder().title(title).link(link).image(image)
                    .date(date).writer(writer).platform(platform).image_type(image_type).build());
        }

        return cardList;
    }
}
