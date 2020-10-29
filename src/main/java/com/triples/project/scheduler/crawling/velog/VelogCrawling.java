package com.triples.project.scheduler.crawling.velog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.triples.project.dao.ICardDao;
import com.triples.project.dao.collection.Card;
import com.triples.project.scheduler.crawling.ICrawling;
import com.triples.project.util.ImageSizeUtil;
import com.triples.project.util.ImageToBase64Util;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;


/**
 * @author : 김석영
 * @URL : https://velog.io
 * @description : URL에 있는 내용을 크롤링하는 클래스
 */
@Component("velogCrawling")
@RequiredArgsConstructor
public class VelogCrawling implements ICrawling {

    // driver 재사용
    private final WebDriver driver;
    private final WebDriverWait webDriverWait;
    private final ICardDao iCardDao;

    private final String url = "https://velog.io/recent";
    private final String platform = "velog";

    @SneakyThrows
    @Override
    public List<Card> startCrawling(){

        List<Card> cardList = new ArrayList<>();

        driver.get(url);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        for(int i = 0; i < 9; i++) { // 각 url 에 따라 설정

            js.executeScript("window.scrollTo(0,document.body.scrollHeight)");

            // Scroll Down 후 데이터 로딩 Wait
            synchronized (webDriverWait) {
                webDriverWait.wait(3000);
            }
        }

        //오늘 날짜
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String today = dateFormat.format(date);

        List<WebElement> list = driver.findElements(By.xpath("//div[@class='sc-jhAzac dvLbCX']"));

        for (WebElement target : list) {
            String link = target.findElement(By.xpath(".//a")).getAttribute("href");
            String title = target.findElement(By.xpath(".//h4")).getAttribute("textContent");
            String description = target.findElement(By.xpath(".//p")).getAttribute("textContent");
            String writer = target.findElement(By.xpath(".//b")).getAttribute("textContent");
            //temp를 쓰고 있는데 이유가 있을까?
            WebElement createdTemp = target.findElement(By.xpath(".//div[@class='sub-info']"));
            String created_at = createdTemp.findElement(By.xpath(".//span")).getAttribute("textContent");
            String image = null;

            WebElement imageTemp = target.findElement(By.xpath(".//div[@class='sc-Rmtcm dxtZdc']"));
            image = ImageToBase64Util.getBase64String(imageTemp.findElement(By.xpath(".//img")).getAttribute("src"));
            String image_type   = ImageSizeUtil.getImageType(image);

            // delete duplication
            List<Card> cards = iCardDao.findByLink(link);
            if(cards.size() > 0) continue;

            cardList.add(
                    Card.builder().
                            title(title).
                            link(link).
                            image(image).
                            description(description).
                            writer(writer).
                            created_at(created_at).
                            date(today).
                            platform(platform).
                            image_type(image_type).
                            build());
        }

        return cardList;
    }
}