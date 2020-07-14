package com.triples.project.configuration.chromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
    @Value("${selenium.webDriver.path}")
    private String CHROME_DRIVER_PATH;

    private WebDriver driver;

    @Bean
    public WebDriver webDriver() {

       // System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);

//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--window-size=1366,768"); // 정확히 어떤 옵션인지 확인 불가..
//        options.addArguments("--headless");
//        options.setProxy(null);                         // ??  정확히 알고 사용 할 것
//        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
//        capabilities.setCapability("pageLoadStrategy", "none");

        WebDriverManager.chromedriver().setup(); // gradle에 설정된 라이브러리가 알아서 set up
                                                 // webdriver 가 설치할 필요 없음

        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        driver = new ChromeDriver(options);

        //driver = new ChromeDriver(); // driver 재사용 test하기 위해서 option 종료
                                     // driver.close 하지 않고도 재사용 가능? 메모리에 계속 남아 있나?
        return driver;
    }


}
