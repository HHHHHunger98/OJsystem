package com.oj.judge.codesandbox.impl;

import com.oj.judge.codesandbox.CodeSandbox;
import com.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.oj.judge.codesandbox.model.ExecuteCodeResponse;
import com.oj.model.dto.problemsubmit.JudgeInfo;
import com.oj.model.enums.JudgeInfoEnum;
import com.oj.model.enums.ProblemSubmitStatusEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Code sandbox example
 */
@Slf4j
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("execution succeed");
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(ProblemSubmitStatusEnum.ACCEPTED.getText());
        judgeInfo.setTime(100L);
        judgeInfo.setSpace(100L);
        executeCodeResponse.setStatus(ProblemSubmitStatusEnum.ACCEPTED.getValue());
        executeCodeResponse.setJudgeInfo(judgeInfo);

        return executeCodeResponse;
    }
}
