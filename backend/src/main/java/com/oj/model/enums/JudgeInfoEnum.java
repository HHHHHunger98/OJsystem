package com.oj.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Judge information enumeration
 *
 * @author <a href="https://github.com/HHHHHunger98">hhhhhunger</a>
 * @from <a href="https://github.com/HHHHHunger98/OJsystem">OJ_system</a>
 */
public enum JudgeInfoEnum {

    ACCEPTED("Submission accepted", "Accepted"),
    JUDGING("Submission judging", "Judging"),
    WRONG("Submission failed", "Wrong"),
    COMPILE_ERROR("Compile error", "Compile error"),
    MEM_LIMIT_EXCEEDED("Memory Limit Exceeded", "Memory limit exceeded"),
    TIME_LIMIT_EXCEEDED("Time Limit Exceeded", "Time limit exceeded"),
    OUTPUT_LIMIT_EXCEEDED("Output Limit Exceeded", "Output limit exceeded"),
    PRESENTATION_ERROR("Presentation Error", "Presentation error"),;

    private final String text;

    private final String value;

    JudgeInfoEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * access the value list
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * access the enumeration by value
     *
     * @param value
     * @return
     */
    public static JudgeInfoEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (JudgeInfoEnum anEnum : JudgeInfoEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
