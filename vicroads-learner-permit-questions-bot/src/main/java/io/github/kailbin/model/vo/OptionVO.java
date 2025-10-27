package io.github.kailbin.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OptionVO {

    private String text;

    public OptionVO(String text) {
        this.text = text;
    }
}
