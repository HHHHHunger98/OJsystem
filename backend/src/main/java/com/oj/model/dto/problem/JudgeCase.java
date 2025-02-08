package com.oj.model.dto.problem;

import lombok.Data;

/**
 * problem judge case
 */
@Data
public class JudgeCase {

    /**
     * input case
     */
    private String input;

    /**
     * output case
     */
    private String output;
}
