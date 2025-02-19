package com.oj.judge.codesandbox.model;

import com.oj.model.dto.problemsubmit.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeResponse {

    /**
     * output group
     */
    private List<String> outputList;

    /**
     * execution information
     */
    private String message;

    /**
     * execution status
     */
    private Integer status;

    /**
     * judging information
     */
    private JudgeInfo judgeInfo;
}
