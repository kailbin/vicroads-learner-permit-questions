package io.github.kailbin.crawler;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static io.github.kailbin.crawler.VicRoadsLearnerPermitConst.URL_ANSWER;
import static io.github.kailbin.crawler.VicRoadsLearnerPermitConst.URL_QUESTIONS;

/**
 * Fetch Page Source: Question and Answer
 *
 * @see VicRoadsLearnerPermitJdk11Crawler
 * @deprecated Use JDK API
 */
class VicRoadsLearnerPermitChromeCrawler implements VicRoadsLearnerPermitCrawlerI {

    private static final int WAIT_SECONDS = 5;

    /**
     * 获取问题列表
     */
    @Override
    public String requestQuestions() throws InterruptedException {

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
    @Override
    public String requestAnswer(String refNumber) throws InterruptedException {
        WebDriver webDriver = WebDriverFactory.defaultInstance();

        // 打开页面
        webDriver.get(URL_ANSWER + refNumber);

        // Waiting Response
        TimeUnit.SECONDS.sleep(WAIT_SECONDS);

        return webDriver.getPageSource();

    }

    @Override
    public byte[] requestImage(String imageUrl) {
        throw new UnsupportedOperationException();
    }


    /**
     * 浏览器驱动，Open Chrome
     */
    public static class WebDriverFactory {

        private static final WebDriver WEB_DRIVER;

        static {
            final WebDriverManager chromedriver = WebDriverManager.chromedriver();
            chromedriver.setup();

            final ChromeOptions chromeOptions = new ChromeOptions();
            // 无窗口模式
            chromeOptions.setHeadless(false);

            chromeOptions.addArguments("--remote-allow-origins=*");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            // 禁止硬件加速，避免严重占用cpu
            chromeOptions.addArguments("--disable-gpu");
            // avoid disabling web security in normal runs — enable only if strictly required
            // chromeOptions.addArguments("--disable-web-security");
            // prefer hiding automation via Blink feature rather than deprecated infobars flag
            chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
            // 隐藏"Chrome正在受到自动软件的控制
            chromeOptions.addArguments("--disable-infobars");
            // UA
            // chromeOptions.addArguments("user-agent=\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36\"");
            //
            // 设置开发者模式启动，该模式下webdriver属性为正常值
            chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

            // 禁止加载 图片 和 CSS
            setExperimentalOption(chromeOptions);

            // https://www.selenium.dev/zh-cn/documentation/webdriver/capabilities/shared/#%E9%A1%B5%E9%9D%A2%E5%8A%A0%E8%BD%BD%E7%AD%96%E7%95%A5
            chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

            // chromeOptions.setPageLoadTimeout(Duration.ofSeconds(10));

            WEB_DRIVER = new ChromeDriver(chromeOptions);
            final WebDriver.Options manage = WEB_DRIVER.manage();
            manage.timeouts().implicitlyWait(Duration.ofMillis(5000));

            //
            Runtime.getRuntime().addShutdownHook(new Thread(WEB_DRIVER::quit));
        }

        public static WebDriver defaultInstance() {
            return WEB_DRIVER;
        }

        private static void setExperimentalOption(final ChromeOptions chromeOptions) {
            final HashMap<Object, Object> map = new HashMap<>();
            // 禁止加载图片
            // map.put("profile.managed_default_content_settings.images", 2);
            // 禁止加载样式
            map.put("permissions.default.stylesheet", 2);
            chromeOptions.setExperimentalOption("prefs", map);
        }

    }
}
