package io.github.kailbin.crawler;

import io.github.kailbin.tools.WebDriverFactory;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

/**
 * Fetch Page Source: Question and Answer
 */
public class VicRoadsLearnerPermitCrawler {

    private static final int WAIT_SECONDS = 5;

    public static final String DOMAIN = "https://www.vicroads.vic.gov.au";

    private static final String URL_QUESTIONS = DOMAIN + "/licences/your-ls/learner-permit-test-inperson/lpt/lptoffline";

    private static final String URL_ANSWER = DOMAIN + "/licences/your-ls/learner-permit-test-inperson/lpt/lptoffline/lptcorrectionsheet?testref=";

    /**
     * 获取问题列表
     */
    public static String requestQuestions() throws InterruptedException {

        WebDriver webDriver = WebDriverFactory.defaultInstance();

        // 打开页面
        webDriver.get(URL_QUESTIONS);

        // Waiting Response
        TimeUnit.SECONDS.sleep(WAIT_SECONDS);

        return webDriver.getPageSource();

    }

    /**
     * 获取问题的答案
     */
    public static String requestAnswer(String refNumber) throws InterruptedException {
        WebDriver webDriver = WebDriverFactory.defaultInstance();

        // 打开页面
        webDriver.get(URL_ANSWER + refNumber);

        // Waiting Response
        TimeUnit.SECONDS.sleep(WAIT_SECONDS);

        return webDriver.getPageSource();

    }

}
