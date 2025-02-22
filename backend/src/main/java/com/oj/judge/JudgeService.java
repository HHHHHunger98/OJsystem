package com.oj.judge;


import com.oj.model.entity.ProblemSubmit;

/**
 * Code judging service
 */
public interface JudgeService {

    /**
     * Do code judging
     * @param problemSubmitId
     * @return
     */
    ProblemSubmit doJudge(long problemSubmitId);
}
