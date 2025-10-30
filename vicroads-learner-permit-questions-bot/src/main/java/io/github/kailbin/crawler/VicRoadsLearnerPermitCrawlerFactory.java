package io.github.kailbin.crawler;

public class VicRoadsLearnerPermitCrawlerFactory {

    private static volatile VicRoadsLearnerPermitCrawlerI jdkHttpClient;

    /**
     * @see #singleInstanceOfJdkHttpClient()
     * @deprecated
     */
    public static VicRoadsLearnerPermitCrawlerI newChromeInstance() {
        return new VicRoadsLearnerPermitChromeCrawler();
    }

    /**
     *
     */
    public static synchronized VicRoadsLearnerPermitCrawlerI singleInstanceOfJdkHttpClient() {
        if (jdkHttpClient == null) {
            jdkHttpClient = new VicRoadsLearnerPermitJdk11Crawler();
        }
        return jdkHttpClient;
    }

}
