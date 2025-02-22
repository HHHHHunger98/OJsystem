package com.oj.judge.judgestrategy;

import com.oj.model.dto.problem.JudgeCase;
import com.oj.model.dto.problemsubmit.JudgeInfo;
import com.oj.model.entity.Problem;
import com.oj.model.entity.ProblemSubmit;
import lombok.Data;

import java.util.List;

/**
 * Context for code execution result judging
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Problem problem;

    private ProblemSubmit problemSubmit;
}
