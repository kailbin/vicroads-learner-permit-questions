package io.github.kailbin;

import io.github.kailbin.crawler.VicRoadsLearnerPermitCrawlerFactory;
import io.github.kailbin.crawler.VicRoadsLearnerPermitCrawlerI;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

class VicRoadsLearnerPermitCrawlerTest {

    @Test
    void requestQuestions() throws IOException, InterruptedException {
        VicRoadsLearnerPermitCrawlerI crawlerI = VicRoadsLearnerPermitCrawlerFactory.singleInstanceOfJdkHttpClient();

        String pageSource = crawlerI.requestQuestions();
        System.out.println(pageSource);
    }

    @Test
    void requestAnswer() throws InterruptedException, IOException {
        VicRoadsLearnerPermitCrawlerI crawlerI = VicRoadsLearnerPermitCrawlerFactory.singleInstanceOfJdkHttpClient();

        String pageSource = crawlerI.requestAnswer("2368528");
        System.out.println(pageSource);
    }


    @Test
    void requestImage() throws InterruptedException, IOException {
        VicRoadsLearnerPermitCrawlerI crawlerI = VicRoadsLearnerPermitCrawlerFactory.singleInstanceOfJdkHttpClient();
        byte[] imageByte = crawlerI.requestImage("https://www.vicroads.vic.gov.au/-/media/images/licences/learner-permit-practice-test/k0189.ashx");
        Path path = Paths.get("/Users/kevin/Downloads/k0189.jpeg");
        Files.write(path, imageByte, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

}