package com.oj.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The language used in the solution code submission
 *
 * @author <a href="https://github.com/HHHHHunger98">hhhhhunger</a>
 * @from <a href="https://github.com/HHHHHunger98/OJsystem">OJ_system</a>
 */
public enum ProblemSubmitLanguageEnum {

    JAVA("Java", "java"),
    CPLUSPLUS("C++", "cpp"),
    PYTHON("Python", "python"),
    GOLANG("Golang", "go");

    private final String text;

    private final String value;

    ProblemSubmitLanguageEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * get values
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * get enumeration by value
     *
     * @param value
     * @return
     */
    public static ProblemSubmitLanguageEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (ProblemSubmitLanguageEnum anEnum : ProblemSubmitLanguageEnum.values()) {
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
