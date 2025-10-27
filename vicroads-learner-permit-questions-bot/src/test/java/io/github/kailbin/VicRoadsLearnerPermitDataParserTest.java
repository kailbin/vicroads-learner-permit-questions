package io.github.kailbin;

import io.github.kailbin.parser.VicRoadsLearnerPermitAnswerParser;
import io.github.kailbin.parser.VicRoadsLearnerPermitQuestionParser;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

class VicRoadsLearnerPermitDataParserTest {

    @Test
    void parseQuestions() throws IOException {
        String htmlFile = "/2368528-questions.html";
        try (InputStream resource = VicRoadsLearnerPermitDataParserTest.class.getResourceAsStream(htmlFile)) {
            String pageSource = String.join(System.lineSeparator(), IOUtils.readLines(resource, StandardCharsets.UTF_8));

            VicRoadsLearnerPermitQuestionParser questionParser = new VicRoadsLearnerPermitQuestionParser(pageSource);

            //
            System.out.println(questionParser.parsePracticeTestRef());
            System.out.println(questionParser.parseQuestions());
        }
    }

    @Test
    void parseAnswer() throws IOException {
        String htmlFile = "/2368528-answer.html";
        try (InputStream resource = VicRoadsLearnerPermitDataParserTest.class.getResourceAsStream(htmlFile)) {
            String pageSource = String.join(System.lineSeparator(), IOUtils.readLines(resource, StandardCharsets.UTF_8));

            VicRoadsLearnerPermitAnswerParser answerDataParser = new VicRoadsLearnerPermitAnswerParser(pageSource);
            System.out.println(answerDataParser.parseAnswers());
            System.out.println(answerDataParser.parseAnswerIndex());

        }
    }

}