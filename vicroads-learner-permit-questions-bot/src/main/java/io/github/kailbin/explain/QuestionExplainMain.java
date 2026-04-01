package io.github.kailbin.explain;

import com.google.common.base.Strings;
import io.github.kailbin.model.po.QuestionPO;
import io.github.kailbin.repository.DataFileDAO;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class QuestionExplainMain {

    static OpenAiMain openAiMain = new OpenAiMain();

    static String prompt = """
            这是一个澳大利亚维多利亚州的驾驶考试题目：
            
            要求：
            1. 翻译与解释，全部支持中英文，不要自己增加标题，只要中英文解释的部分
            - 回答简洁明了，简练精确，同时包含中英文，需要单页显示，不能过多
            - 翻译当前问题，在正确的选项前面打钩 ✅ 错误的问题前面 ❌，注意：有且仅有一个正确答案
            2. 以 markdown 的形式告诉我，放在 ``` 中，仅返回 markdown 即可，只用文本，无需表格
            
            示例
            ```
            这个标志表示周一至周五下午3:30-6:30之间会发生什么？
            - ❌ Tow trucks are allowed to park in this area.（拖车被允许在此区域停车。）
            - ❌ Tow trucks have right of way over vehicles in this area.（拖车在此区域享有优先通行权。）
            - ✅ Your vehicle will be towed away if you park in this area.（如果您在此区域停车，您的车辆将被拖走。）
            
            ------
            
            This is a standard tow-away zone ....
            这是一个标准的拖车区标志。在执法时间....
            ```
             /no_think
            """;

    public static void main(String[] args) throws IOException {
        // Read
        Map<String, QuestionPO> allQuestionPOMap = DataFileDAO.readAllQuestionMappings();
        for (QuestionPO questionPO : allQuestionPOMap.values()) {
            String explain = questionPO.getExplain();
            if (explain != null) {
                continue;
            }

            StringBuilder fullQuestion = new StringBuilder();
            fullQuestion.append("Question: ").append(questionPO.getTitle()).append(System.lineSeparator());
            questionPO.getAnswers().forEach(answer -> {
                fullQuestion
                        .append("Option")
                        .append(" (").append(answer.isCorrect() ? "正确" : "错误").append(") :")
                        .append(answer.getOption())
                        .append(System.lineSeparator());
            });
            fullQuestion.append(System.lineSeparator());
            System.out.println(fullQuestion);

            String imageBase64 = questionPO.getImageBase64();
            String userInput = fullQuestion + System.lineSeparator() + prompt;
            String response = openAiMain.getResponseWithImage(userInput, imageBase64);
            System.out.println(response);

            questionPO.setExplain(response.replace("```", "").trim());
            DataFileDAO.writeAllQuestions(new ArrayList<>(allQuestionPOMap.values()));
        }
    }

}
