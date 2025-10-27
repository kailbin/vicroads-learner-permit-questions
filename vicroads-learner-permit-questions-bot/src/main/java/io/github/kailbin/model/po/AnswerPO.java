
package io.github.kailbin.model.po;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerPO {

    /**
     * 是否是正确答案
     */
    private boolean correct = false;

    /**
     * 选项内容
     */
    private String option;

}