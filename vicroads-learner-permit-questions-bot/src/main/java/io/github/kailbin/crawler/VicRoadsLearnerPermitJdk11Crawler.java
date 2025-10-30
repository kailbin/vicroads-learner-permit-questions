package io.github.kailbin.crawler;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static io.github.kailbin.crawler.VicRoadsLearnerPermitConst.URL_ANSWER;
import static io.github.kailbin.crawler.VicRoadsLearnerPermitConst.URL_QUESTIONS;

/**
 * Fetch Page Source: Question and Answer
 */
class VicRoadsLearnerPermitJdk11Crawler implements VicRoadsLearnerPermitCrawlerI {

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .version(HttpClient.Version.HTTP_2)
            .build();

    /**
     * 获取问题列表
     */
    @Override
    public String requestQuestions() throws IOException, InterruptedException {

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(URI.create(URL_QUESTIONS));
        //
        HttpRequest httpRequest = this.withDefaultHeaders(requestBuilder)
                .GET()
                .build();

        HttpResponse<String> response = HTTP_CLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }


    /**
     * 获取问题的答案
     */
    @Override
    public String requestAnswer(String refNumber) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(URI.create(URL_ANSWER + refNumber));
        //
        HttpRequest httpRequest = this.withDefaultHeaders(requestBuilder)
                .GET()
                .build();

        HttpResponse<String> response = HTTP_CLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return response.body();

    }

    @Override
    public byte[] requestImage(String imageUrl) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(URI.create(imageUrl));
        //
        HttpRequest httpRequest = this.withDefaultHeaders(requestBuilder)
                .GET()
                .build();

        HttpResponse<byte[]> response = HTTP_CLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());

        return response.body();
    }


    private HttpRequest.Builder withDefaultHeaders(HttpRequest.Builder requestBuilder) {
        return requestBuilder
                // Simulate browser headers
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/141.0.0.0 Safari/537.36")
                .header("sec-ch-ua", "\"Google Chrome\";v=\"141\", \"Not?A_Brand\";v=\"8\", \"Chromium\";v=\"141\"")
                .header("sec-ch-ua-mobile", " ?0")
                .header("sec-ch-ua-platform", "\"macOS\"")
                //
                // Request preference: client prefers secure (HTTPS) resources; "1" requests upgrade
                .header("upgrade-insecure-requests", "1")
                // Fetch metadata: destination of the request (document, image, etc.)
                //.header("sec-fetch-dest", "document")
                // Fetch metadata: request mode (navigate, cors, no-cors)
                // .header("sec-fetch-mode", "navigate")
                // Fetch metadata: origin relationship (same-origin, same-site, cross-site, none)
                //.header("sec-fetch-site", "none")
                // Fetch metadata: whether request was triggered by user activation ("?1" means yes)
                //.header("sec-fetch-user", "?1")
                ;
    }


}
