package io.github.kailbin.crawler;

import io.github.kailbin.crawler.VicRoadsLearnerPermitChromeCrawler;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

class WebDriverFactoryTest {

    @Test
    void getWebDriver() throws InterruptedException {

        WebDriver webDriver = VicRoadsLearnerPermitChromeCrawler.WebDriverFactory.defaultInstance();

        // 打开页面
        webDriver.get("https://www.vicroads.vic.gov.au/");

        // Waiting Response
        TimeUnit.SECONDS.sleep(3L);

        System.out.println(webDriver.getPageSource());
    }

}