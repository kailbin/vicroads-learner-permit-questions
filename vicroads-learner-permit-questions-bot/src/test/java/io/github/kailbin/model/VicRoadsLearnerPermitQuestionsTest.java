package io.github.kailbin.model;

import io.github.kailbin.model.vo.OptionVO;
import io.github.kailbin.model.vo.QuestionVO;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class VicRoadsLearnerPermitQuestionsTest {

    @Test
    void convert() {

        QuestionVO questionVO = new QuestionVO();
        questionVO.setTitle("this is a title");
        questionVO.setOptions(Arrays.asList(
                new OptionVO("a"),
                new OptionVO("b"),
                new OptionVO("c")
        ));

        VicRoadsLearnerPermitQuestions questions = VicRoadsLearnerPermitQuestions.convertCurrent(Collections.singletonList(questionVO));
        System.out.println(questions);
    }
}