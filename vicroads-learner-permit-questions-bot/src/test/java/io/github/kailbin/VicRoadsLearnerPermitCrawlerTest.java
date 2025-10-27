package io.github.kailbin;

import io.github.kailbin.crawler.VicRoadsLearnerPermitCrawler;
import org.junit.jupiter.api.Test;

class VicRoadsLearnerPermitCrawlerTest {

    @Test
    void requestQuestions() throws InterruptedException {
        String pageSource = VicRoadsLearnerPermitCrawler.requestQuestions();
        System.out.println(pageSource);
    }

    @Test
    void requestAnswer() throws InterruptedException {
        String pageSource = VicRoadsLearnerPermitCrawler.requestAnswer("2368528");
        System.out.println(pageSource);
    }

}