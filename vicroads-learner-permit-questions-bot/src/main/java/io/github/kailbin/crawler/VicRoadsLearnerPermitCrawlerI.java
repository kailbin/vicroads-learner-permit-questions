package io.github.kailbin.crawler;

import java.io.IOException;

public interface VicRoadsLearnerPermitCrawlerI {

    String requestQuestions() throws IOException, InterruptedException;

    String requestAnswer(String refNumber) throws IOException, InterruptedException;

    byte[] requestImage(String imageUrl) throws IOException, InterruptedException;

}
