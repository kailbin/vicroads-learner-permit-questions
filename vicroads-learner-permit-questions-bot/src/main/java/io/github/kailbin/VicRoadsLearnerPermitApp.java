package io.github.kailbin;

import io.github.kailbin.crawler.VicRoadsLearnerPermitCrawlerFactory;
import io.github.kailbin.crawler.VicRoadsLearnerPermitCrawlerI;
import io.github.kailbin.model.*;
import io.github.kailbin.model.po.QuestionPO;
import io.github.kailbin.model.vo.QuestionVO;
import io.github.kailbin.parser.VicRoadsLearnerPermitAnswerParser;
import io.github.kailbin.parser.VicRoadsLearnerPermitQuestionParser;
import io.github.kailbin.repository.DataFileDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @see VicRoadsLearnerPermitCrawlerFactory Datasource
 * @see VicRoadsLearnerPermitQuestionParser Question Parser
 * @see VicRoadsLearnerPermitAnswerParser Answer Parser
 */
public class VicRoadsLearnerPermitApp {

    public static void main(String[] args) throws InterruptedException, IOException {
        VicRoadsLearnerPermitCrawlerI crawlerI = VicRoadsLearnerPermitCrawlerFactory.singleInstanceOfJdkHttpClient();

        // 问题 HTML
        String questionsSource = crawlerI.requestQuestions();
        VicRoadsLearnerPermitQuestionParser questionDataParser = new VicRoadsLearnerPermitQuestionParser(questionsSource);
        String refNumber = questionDataParser.parsePracticeTestRef();
        List<QuestionVO> questionVOs = questionDataParser.parseQuestions();
        //
        VicRoadsLearnerPermitQuestions permitQuestions = VicRoadsLearnerPermitQuestions.convertCurrent(questionVOs);

        // 答案 HTML
        String answerSource = crawlerI.requestAnswer(refNumber);
        VicRoadsLearnerPermitAnswerParser answerDataParser = new VicRoadsLearnerPermitAnswerParser(answerSource);
        List<Integer> answersIndex = answerDataParser.parseAnswerIndex();
        // 设置正确答案
        permitQuestions.fillCorrectAnswers(answersIndex);


        // Read
        Map<String, QuestionPO> allQuestionPOMap = DataFileDAO.readAllQuestionMappings();
        // Merge
        permitQuestions.merge2AllQuestionPOs(allQuestionPOMap);
        // Save
        DataFileDAO.writeAllQuestions(new ArrayList<>(allQuestionPOMap.values()));
    }
}
