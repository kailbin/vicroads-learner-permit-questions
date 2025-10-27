package io.github.kailbin.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Question Parser
 */
public class VicRoadsLearnerPermitAnswerParser {

    private final Document document;

    public VicRoadsLearnerPermitAnswerParser(String pageSourceHtml) {
        this.document = Jsoup.parse(pageSourceHtml);
    }

    /**
     * 测试编号
     */
    public List<String> parseAnswers() {
        Elements elements = document.select(".lpt-answersheet tr");

        return Optional.of(elements).orElse(new Elements(Collections.emptyList()))
                .stream()
                .map(element -> element.select("td").text().trim())
                .filter(a -> !a.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * a > 0
     * b > 1
     * c > 2
     */
    public List<Integer> parseAnswerIndex() {
        return parseAnswers().stream().map(a -> a.charAt(0) - 'a').collect(Collectors.toList());
    }

}
