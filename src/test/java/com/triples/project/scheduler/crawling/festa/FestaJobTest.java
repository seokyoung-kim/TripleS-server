package com.triples.project.scheduler.crawling.festa;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sound.midi.Soundbank;
import java.awt.image.ImagingOpException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FestaJobTest {

    @Autowired
    private WebDriver driver; // bean으로 한번만 설정하여 재사용 할수 있도록

    @After
    public void cleanUp() {
        // 드라이버만 종료

        // driver.quit() 할경우 모든 셀레니움의 세션이 종료 되므로
        // bean으로 설정되어 있는 WebDriver 도 종료가 되므로 driver.close만 작성
        driver.close();
    }

    @Test
    public void test() {


        String url = "https://festa.io/";
        driver.get(url);

        List<WebElement> list = driver.findElements(By.xpath("//a[@class='EventCardMobile__Card-sc-1do1vt8-0 dHIdtb']"));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>" + list.size());
        for(WebElement target : list) {

            String link = target.findElement(By.xpath(".//div[@src]")).getAttribute("src");;

            String title = target.findElement(By.xpath(".//h3")).getAttribute("textContent");
            String image = target.getAttribute("href");
            System.out.println(link);
            System.out.println(title);
            System.out.println(image);
        }
    }

}
