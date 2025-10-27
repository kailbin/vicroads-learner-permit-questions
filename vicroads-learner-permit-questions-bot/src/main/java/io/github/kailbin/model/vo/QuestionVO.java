package io.github.kailbin.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Slf4j
public class QuestionVO {



    private String title;

    private String imageUrl;

    private List<OptionVO> options;

    public void prettyPrint() {
        if (!log.isInfoEnabled()) {
            return;
        }
        log.info("Title: {}", title);
        log.info("Image: {}", (null == imageUrl ? "" : imageUrl));
        if (options != null) {
            log.info(options.stream().map(OptionVO::getText).collect(Collectors.joining(System.lineSeparator())));
        }
    }
}