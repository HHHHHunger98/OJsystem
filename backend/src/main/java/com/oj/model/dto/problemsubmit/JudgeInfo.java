package com.oj.model.dto.problemsubmit;

import lombok.Data;

/**
 * information for judging
 */
@Data
public class JudgeInfo {

    /**
     * message: program executing information
     */
    private String message;

    /**
     * time: time costed
     */
    private Long time;

    /**
     * space: storage used
     */
    private Long space;
}
