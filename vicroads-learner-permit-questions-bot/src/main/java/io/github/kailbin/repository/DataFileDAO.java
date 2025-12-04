package io.github.kailbin.repository;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import io.github.kailbin.model.po.QuestionPO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class DataFileDAO {

    private static final String MODEL_DATA_FILE_PATH = "vicroads-learner-permit-questions-front/public/data";

    public static final String DATA_FILE_NAME = "vicroads_learner_permit_questions.json";

    public static List<QuestionPO> readAllQuestions() throws IOException {

        String rootPath = getRootPath();
        if (StringUtils.isBlank(rootPath)) {
            log.error("Root path is blank, can't read data file");
            return new ArrayList<>();
        }
        File dataFile = Paths.get(rootPath, DATA_FILE_NAME).toFile();

        String jsonData = FileUtils.readFileToString(dataFile, StandardCharsets.UTF_8);

        return JSON.parseArray(jsonData, QuestionPO.class);
    }

    public static Map<String, QuestionPO> readAllQuestionMappings() throws IOException {
        List<QuestionPO> questionPOS = readAllQuestions();
        return questionPOS.stream()
                .collect(Collectors.toMap(
                        QuestionPO::getId,
                        Function.identity(),
                        (q1, q2) -> q1)
                );
    }

    public static void writeAllQuestions(List<QuestionPO> allQuestions) throws IOException {
        String rootPath = getRootPath();
        if (StringUtils.isBlank(rootPath)) {
            log.error("Root path is blank, can't read data file");
            return;
        }
        File dataFile = Paths.get(rootPath, DATA_FILE_NAME).toFile();

        // sort by createTime desc, id asc
        allQuestions.sort(Comparator.comparing(QuestionPO::getCreateTime).reversed().thenComparing(QuestionPO::getId));

        String jsonString = JSON.toJSONString(allQuestions, JSONWriter.Feature.PrettyFormat);

        FileUtils.write(dataFile, jsonString, StandardCharsets.UTF_8);
    }


    private static String getRootPath() {
        String confPath = System.getProperty("vicroads.pl.questions.data.path");
        if (StringUtils.isNotBlank(confPath)) {
            return confPath;
        }
        try {
            return defaultRootPath();
        } catch (URISyntaxException e) {
            log.warn("Can't get root path", e);
        }
        return null;
    }

    private static String defaultRootPath() throws URISyntaxException {
        URL resource = DataFileDAO.class.getResource("/");
        // /ROOT/vicroads-learner-permit-questions-bot/target/classes/ >> /ROOT
        Path parent = Paths.get(resource.toURI()).getParent().getParent().getParent();
        return Paths.get(parent.toFile().getAbsolutePath(), MODEL_DATA_FILE_PATH).toAbsolutePath().toString();
    }

}
