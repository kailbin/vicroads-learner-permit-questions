package io.github.kailbin.tools;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class WebDriverFactoryTest {

    @Test
    void getWebDriver() throws InterruptedException {

        WebDriver webDriver = WebDriverFactory.defaultInstance();

        // 打开页面
        webDriver.get("https://www.vicroads.vic.gov.au/");

        // Waiting Response
        TimeUnit.SECONDS.sleep(3L);

        System.out.println(webDriver.getPageSource());
    }

}