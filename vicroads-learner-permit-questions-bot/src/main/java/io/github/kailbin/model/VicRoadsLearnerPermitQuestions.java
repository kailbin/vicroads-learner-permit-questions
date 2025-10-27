package io.github.kailbin.model;

import io.github.kailbin.model.po.AnswerPO;
import io.github.kailbin.model.po.QuestionPO;
import io.github.kailbin.model.vo.OptionVO;
import io.github.kailbin.model.vo.QuestionVO;
import io.github.kailbin.tools.ImageDownloadUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Data Model
 */
@Slf4j
public class VicRoadsLearnerPermitQuestions {


    /**
     * 当前抓取的问题
     */
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private List<QuestionPO> currentQuestionPOs;


    /**
     * VO > PO
     */
    public static VicRoadsLearnerPermitQuestions convertCurrent(List<QuestionVO> questions) {
        VicRoadsLearnerPermitQuestions permitQuestions = new VicRoadsLearnerPermitQuestions();

        List<QuestionPO> questionPOS = Convertor.INSTANCE.voToPoBatch(questions);
        permitQuestions.setCurrentQuestionPOs(questionPOS);

        return permitQuestions;
    }

    /**
     * set correct answer
     */
    public void fillCorrectAnswers(List<Integer> answersIndex) {
        if (CollectionUtils.isEmpty(currentQuestionPOs) || CollectionUtils.isEmpty(answersIndex) || currentQuestionPOs.size() != answersIndex.size()) {
            log.error("Data Error question:{}, answer:{}", CollectionUtils.size(currentQuestionPOs), CollectionUtils.size(answersIndex));
        }

        for (int i = 0; i < currentQuestionPOs.size(); i++) {
            QuestionPO questionPO = currentQuestionPOs.get(i);
            Integer answerIndex = answersIndex.get(i);
            //
            questionPO.getAnswers().get(answerIndex).setCorrect(true);
        }

        //
        this.validCurrentQuestions();
    }


    /**
     *
     */
    public void merge2AllQuestionPOs(Map<String, QuestionPO> allQuestionPOs) {
        for (QuestionPO questionPO : this.currentQuestionPOs) {
            this.merge2AllQuestionPO(allQuestionPOs, questionPO);
        }
    }

    private void merge2AllQuestionPO(Map<String, QuestionPO> allQuestionPOs, QuestionPO questionPO) {
        String questionId = calcQuestionId(questionPO);
        String title = questionPO.getTitle();
        LocalDateTime now = LocalDateTime.now();
        //
        QuestionPO existQuestion = allQuestionPOs.get(questionId);
        if (null != existQuestion) {
            log.info("Duplicate question id:{}, title:{}", questionId, title);
            existQuestion.setUpdateTime(now);
            return;
        }

        //
        questionPO.setId(questionId);
        //
        String imageUrl = questionPO.getImageUrl();
        if (StringUtils.isNotBlank(imageUrl)) {
            try {
                String imageBase64 = ImageDownloadUtil.downloadImageAsBase64(imageUrl);
                questionPO.setImageBase64(imageBase64);
            } catch (IOException e) {
                log.warn("Image download error, question id:{}, title:{}，imageUrl:{}", questionId, title, imageUrl, e);
                return;
            }
        }

        // success added
        questionPO.setCreateTime(now);
        questionPO.setUpdateTime(now);
        allQuestionPOs.put(questionId, questionPO);
    }

    /**
     * 计算 Question Id
     */
    String calcQuestionId(QuestionPO questionPO) {
        String title = questionPO.getTitle().replaceAll("\\s", "");

        String sortedOptions = questionPO.getAnswers().stream()
                .map(AnswerPO::getOption)
                .map(s -> s.replaceAll("\\s", ""))
                .sorted()
                .collect(Collectors.joining());

        return DigestUtils.sha1Hex((title + sortedOptions).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 校验当前抓取数据的完整性
     */
    private void validCurrentQuestions() {
        if (currentQuestionPOs == null || currentQuestionPOs.isEmpty()) {
            throw new IllegalArgumentException("questions is null or empty");
        }
        if (currentQuestionPOs.size() != 32) {
            throw new IllegalArgumentException("questions size is not 32");
        }

        for (QuestionPO question : currentQuestionPOs) {
            String title = question.getTitle();
            //
            if (title == null || title.isEmpty()) {
                throw new IllegalArgumentException("question's title is null or empty");
            }

            List<AnswerPO> answers = question.getAnswers();
            if (answers == null || answers.isEmpty()) {
                throw new IllegalArgumentException("question's answers is null or empty");
            }
            if (answers.size() != 3) {
                throw new IllegalArgumentException("questions size is not 3");
            }
            Map<Boolean, List<AnswerPO>> resultGroup = answers.stream().collect(Collectors.groupingBy(AnswerPO::isCorrect));
            if (resultGroup.get(true).size() != 1) {
                throw new IllegalArgumentException("questions size is not 3");
            }
            if (resultGroup.get(false).size() != 2) {
                throw new IllegalArgumentException("questions size is not 3");
            }
        }
    }

    @Mapper
    public interface Convertor {

        Convertor INSTANCE = Mappers.getMapper(Convertor.class);

        List<QuestionPO> voToPoBatch(List<QuestionVO> vo);

        @Mapping(target = "answers", source = "options")
        QuestionPO voToPo(QuestionVO vo);

        @Mapping(target = "option", source = "text")
        AnswerPO voToPo(OptionVO vo);

    }

}
