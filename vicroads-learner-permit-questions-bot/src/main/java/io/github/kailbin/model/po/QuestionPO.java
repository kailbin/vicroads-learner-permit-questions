package io.github.kailbin.model.po;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class QuestionPO {

    private String title;

    private String imageUrl;

    private List<AnswerPO> answers;

    /**
     * 构建问题 ID = MD5(title)
     */
    private String id;

    /**
     * 图片二进制数据
     */
    private String imageBase64;

    /**
     * 首次获取到的时间
     */
    private LocalDateTime createTime;

    /**
     * 最一次抓取到的时间
     */
    private LocalDateTime updateTime;

}