package com.oj.model.dto.problem;

import lombok.Data;

/**
 * problem judge case
 */
@Data
public class JudgeConfig {

    /**
     * time limit: ms
     * space limit: KB
     * stack limit: KB
     */
    private Long timeLimit;
    private Long spaceLimit;
    private Long stackLimit;
}
