package io.github.kailbin.parser;

import io.github.kailbin.crawler.VicRoadsLearnerPermitCrawler;
import io.github.kailbin.model.vo.OptionVO;
import io.github.kailbin.model.vo.QuestionVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Question Parser
 */
@Slf4j
public class VicRoadsLearnerPermitQuestionParser {

    private final Document document;

    public VicRoadsLearnerPermitQuestionParser(String pageSourceHtml) {
        this.document = Jsoup.parse(pageSourceHtml);
    }

    /**
     * 测试编号
     */
    public String parsePracticeTestRef() {
        Elements elements = document.select(".lpt-printsummary li:first-child");
        if (CollectionUtils.isEmpty(elements)) {
            return null;
        }

        // Practice Test Ref #: 2368528
        String text = elements.text();

        String[] labelAndNumber = text.split(":");

        return labelAndNumber.length > 1 ? labelAndNumber[1] : null;
    }

    public List<QuestionVO> parseQuestions() {
        Elements questionAreas = document.select(".lpt-area");
        List<QuestionVO> questions = new ArrayList<>();

        for (Element questionArea : questionAreas) {
            // first class="lpt-area clearfix" is not question
            if (questionArea.classNames().contains("clearfix")) {
                continue;
            }

            String title = parseQuestionTitle(questionArea);
            String imageUrl = parseQuestionImage(questionArea);
            // answer's options but no correct selection
            List<OptionVO> options = parseQuestionOptions(questionArea);

            QuestionVO questionVO = new QuestionVO();
            questionVO.setTitle(title);
            if (StringUtils.isNotBlank(imageUrl)) {
                questionVO.setImageUrl(VicRoadsLearnerPermitCrawler.DOMAIN + imageUrl);
            }
            questionVO.setOptions(options);

            //
            questionVO.prettyPrint();
            questions.add(questionVO);
        }

        return questions;
    }

    /**
     * 问题 Title
     */
    private String parseQuestionTitle(Element questionArea) {
        String titleWithNumber = Optional.of(questionArea.select(" > h3"))
                .map(Elements::text)
                .orElse("");

        String[] titleWithNumberArr = titleWithNumber.split("\\.", 2);

        return titleWithNumberArr.length > 1 ? titleWithNumberArr[1].trim() : null;
    }

    /**
     * 问题 Image
     */
    @Nullable
    private String parseQuestionImage(Element questionArea) {
        return Optional.of(questionArea.select(".answer-image img"))
                .map(Elements::first)
                .map(image -> image.attr("src"))
                .orElse(null);
    }

    /**
     * 问题 Options
     */
    private List<OptionVO> parseQuestionOptions(Element questionArea) {
        Elements optionElements = questionArea.select(".answer-options .lpt-questionsheet tr td:last-child");
        //
        List<OptionVO> options = new ArrayList<>();
        for (Element optionElement : optionElements) {
            options.add(new OptionVO(optionElement.text()));
        }
        return options;
    }
}
