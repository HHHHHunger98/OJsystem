package com.oj.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Problem submit status enumeration
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
public enum ProblemSubmitStatusEnum {

    // judging status(0-pending, 1-judging, 2-accepted, 3-failed)
    PENDING("pending", 0),
    JUDGING("judging", 1),
    ACCEPTED("accepted", 2),
    FAILED("accepted", 3);

    private final String text;

    private final Integer value;

    ProblemSubmitStatusEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    /**
     * Get value list
     *
     * @return
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * Get enumeration by value
     *
     * @param value
     * @return
     */
    public static ProblemSubmitStatusEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (ProblemSubmitStatusEnum anEnum : ProblemSubmitStatusEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
