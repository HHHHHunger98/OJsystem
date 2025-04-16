package com.oj.judge.judgestrategy;

import cn.hutool.json.JSONUtil;
import com.oj.model.dto.problem.JudgeCase;
import com.oj.model.dto.problem.JudgeConfig;
import com.oj.judge.codesandbox.model.JudgeInfo;
import com.oj.model.entity.Problem;
import com.oj.model.enums.JudgeInfoEnum;

import java.util.List;

/**
 * Default judging strategy based on default context
 */
public class DefaultStrategy implements JudgeStrategy {

    /**
     * Do the judging of the solution code execution result
     * @param judgeContext
     * @return
     */
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        Problem problem = judgeContext.getProblem();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        Long time = judgeInfo.getTime();
        Long space = judgeInfo.getSpace();
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setTime(time);
        judgeInfoResponse.setSpace(space);

        // Set the default value to judging
        JudgeInfoEnum judgeInfoEnum = JudgeInfoEnum.ACCEPTED;
        judgeInfoResponse.setMessage(judgeInfoEnum.getValue());
        // The input and output size should be the same
        if (outputList.size() != inputList.size()) {
            judgeInfoEnum = JudgeInfoEnum.WRONG;
            judgeInfoResponse.setMessage(judgeInfoEnum.getValue());
            return judgeInfoResponse;
        }
        // Iterate through the outputList and the expectedOutputList to check whether they are equal
        for (int i = 0; i < outputList.size(); i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            if (!judgeCase.getOutput().equals(outputList.get(i))) {
                judgeInfoEnum = JudgeInfoEnum.WRONG;
                judgeInfoResponse.setMessage(judgeInfoEnum.getValue());
                return judgeInfoResponse;
            }
        }
        // Check whether the space and time exceed
        String judgeConfigStr = problem.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        Long expectedSpaceLimit = judgeConfig.getSpaceLimit();
        Long expectedTimeLimit = judgeConfig.getTimeLimit();
        if (space > expectedSpaceLimit) {
            judgeInfoEnum = JudgeInfoEnum.MEM_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoEnum.getValue());
            return judgeInfoResponse;
        }
        if (time > expectedTimeLimit) {
            judgeInfoEnum = JudgeInfoEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoEnum.getValue());
            return judgeInfoResponse;
        }

        judgeInfoResponse.setMessage(judgeInfoEnum.getValue());
        return judgeInfoResponse;
    }
}
