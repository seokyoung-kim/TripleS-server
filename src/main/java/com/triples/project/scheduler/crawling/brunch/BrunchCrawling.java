package com.triples.project.scheduler.crawling.brunch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.triples.project.dao.collection.Card;
import com.triples.project.scheduler.crawling.ICrawling;

import lombok.RequiredArgsConstructor;


/**
 * @author : 변광진
 * @URL : brunch
 * @description : URL에 있는 내용을 크롤링하는 클래스
 */
@Component("brunchCrawling")
@RequiredArgsConstructor
public class BrunchCrawling implements ICrawling {

	// driver 재사용
	private final WebDriver driver;
	
	@Override
	public List<Card> startCrawling() throws InterruptedException {

		List<Card> cardList = new ArrayList<>();

		String url = "https://brunch.co.kr/search?q=spring boot&type=article";
		driver.get(url);

		WebDriverWait buffer = new WebDriverWait(driver, 10);
		
		//데이터가 로딩되는데 시간이 걸리기 때문에 로딩 시간을 기다려 줘야 함.
		synchronized (buffer) {
			buffer.wait(5000);
		}
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Boolean heightFlag = true;
		//브라우져 높이
		int browerHeight = Integer.parseInt(js.executeScript("return document.body.scrollHeight").toString());
		System.out.println("browerHeigth : " + browerHeight);
		int compareBrowerHeight = 0;
		
		while(heightFlag) {
			js.executeScript("window.scrollTo(0,  document.body.scrollHeight);");
			//js.executeScript("window.scrollTo(0, "+ browerHeight + 100 +");");
			synchronized (buffer) {
				buffer.wait(1000);
			}
			compareBrowerHeight = Integer.parseInt(js.executeScript("return document.body.scrollHeight").toString());
			if((compareBrowerHeight-browerHeight) > 0) {
				browerHeight = compareBrowerHeight;
			} else {
				heightFlag = false;
			}
		}
		
		List<WebElement> contents = driver.findElements(By.cssSelector("#resultArticle > div > div.result_article > div.wrap_article_list > ul > li > a"));
		
		/*
		 * for(WebElement element : contents) { System.out.println("Title : " +
		 * element.findElement(By.cssSelector("strong")).getText());
		 * System.out.println("본문 : " +
		 * element.findElement(By.cssSelector("div.wrap_sub_content")).getText());
		 * System.out.println("공유수 : " +
		 * element.findElement(By.cssSelector("span > span:nth-child(2)")).getText());
		 * System.out.println("댓글수 : " +
		 * element.findElement(By.cssSelector("span > span:nth-child(5)")).getText());
		 * System.out.println("작성자 : " +
		 * element.findElement(By.cssSelector("span > span:nth-child(10)")).getText());
		 * System.out.println("작성일 : " +
		 * element.findElement(By.cssSelector("span > span.publish_time")).getText());
		 * System.out.println("경로 : " + element.getAttribute("href")); try {
		 * System.out.println("사진경로 : " +
		 * element.findElement(By.cssSelector("div.post_thumb > img")).getAttribute(
		 * "src")); } catch (Exception e) { System.out.println("사진없음"); } }
		 */
		
		//오늘 날짜
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String today	   = dateFormat.format(date);

		for(WebElement content : contents) {
			
			String title	   = content.findElement(By.cssSelector("strong")).getText();
			String description = content.findElement(By.cssSelector("div.wrap_sub_content")).getText();
			String writer	   = content.findElement(By.cssSelector("span > span:nth-child(10)")).getText();
			String created_at  = content.findElement(By.cssSelector("span > span.publish_time")).getText();
			String link		   = content.getAttribute("href");
			String image	   = "";
			try {
				image	   = content.findElement(By.cssSelector("div.post_thumb > img")).getAttribute("src");
			} catch (Exception e) {
				System.out.println("사진 없음");
				image	   = "None";
			}
			
			cardList.add(
					Card.builder()
					.title(title)
					.description(description)
					.writer(writer)
					.created_at(created_at)
					.link(link)
					.date(today)
					.image(image)
					.platform("brunch")
					.category("spring boot")
					.build()
			);
		}
		return cardList;
	}
}
