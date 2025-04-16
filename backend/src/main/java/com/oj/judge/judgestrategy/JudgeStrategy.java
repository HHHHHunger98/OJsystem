package com.oj.judge.judgestrategy;

import com.oj.judge.codesandbox.model.JudgeInfo;

/**
 * Judging strategy
 */
public interface JudgeStrategy {

    /**
     * Do the solution execution result judging based on a specific context
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
