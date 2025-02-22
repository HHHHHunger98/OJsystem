package com.oj.judge;

import com.oj.judge.judgestrategy.DefaultStrategy;
import com.oj.judge.judgestrategy.JavaJudgeStrategy;
import com.oj.judge.judgestrategy.JudgeContext;
import com.oj.judge.judgestrategy.JudgeStrategy;
import com.oj.model.dto.problemsubmit.JudgeInfo;

import com.oj.model.entity.ProblemSubmit;
import org.springframework.stereotype.Service;


/**
 * Manager for judging strategy (simplify the code of caller)
 */
@Service
public class JudgeManager {

    /**
     * Do submit with a specific strategy
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        ProblemSubmit problemSubmit = judgeContext.getProblemSubmit();
        String language = problemSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
