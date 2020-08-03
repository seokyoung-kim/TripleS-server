package com.triples.project.configuration.chromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WebDriver Setup 및 bean 등록하여 재사용 하기 위한 Context
 */
@Configuration
public class ChromeDriverContext {

    // window 는 .exe 붙여야 한다.
    // WebDriverManager Test 아무 이상 없을 경우 제거 할 것
//    @Value("${selenium.webDriver.path}")
//    private String CHROME_DRIVER_PATH;

    private WebDriver driver;
    private WebDriverWait webDriverWait;

    @Bean
    public WebDriver webDriver() {

       // System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);

//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--window-size=1366,768");
//        options.addArguments("--headless");
//        options.setProxy(null);                         // ??  정확히 알고 사용 할 것
//        DesiredCapabilities capabilities = DesiredCapabilities.chrome(); // ?
//        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
//        capabilities.setCapability("pageLoadStrategy", "none");


        // WebDriverManager 라이브러리에 의해서 자동으로 webDriver setup
        // webdriver 가 설치할 필요 없음
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        options.addArguments("headless");    // WebDriver 화면 background 실행
        options.addArguments("no-sandbox");  // linux 서버에서 사용하기 위한 설정
        options.addArguments("disable-gpu"); // 속도 향상을 위해 gpu 제거
        driver = new ChromeDriver(options);
        driver.manage().window().maximize(); // browser 창 최대화

        return driver;
    }

    @Bean
    public WebDriverWait webDriverWait() {
        webDriverWait
                = new WebDriverWait(driver, 20);

        return webDriverWait;
    }


}
