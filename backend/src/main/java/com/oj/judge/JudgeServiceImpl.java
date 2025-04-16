package com.oj.judge;

import cn.hutool.json.JSONUtil;
import com.oj.common.ErrorCode;
import com.oj.exception.BusinessException;
import com.oj.judge.codesandbox.CodeSandbox;
import com.oj.judge.codesandbox.CodeSandboxFactory;
import com.oj.judge.codesandbox.CodeSandboxProxy;
import com.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.oj.judge.codesandbox.model.ExecuteCodeResponse;
import com.oj.judge.judgestrategy.JudgeContext;
import com.oj.model.dto.problem.JudgeCase;
import com.oj.judge.codesandbox.model.JudgeInfo;
import com.oj.model.entity.Problem;
import com.oj.model.entity.ProblemSubmit;
import com.oj.model.enums.ProblemSubmitStatusEnum;
import com.oj.service.ProblemService;
import com.oj.service.ProblemSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private ProblemService problemService;

    @Resource
    private ProblemSubmitService problemSubmitService;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:example}")
    private String type;

    @Override
    public ProblemSubmit doJudge(long problemSubmitId) {

        // 1. Enter the submission Id, get the corresponding problem and submission information, including solution and language
        ProblemSubmit problemSubmit = problemSubmitService.getById(problemSubmitId);
        if (problemSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "No submission found");
        }
        Long problemId = problemSubmit.getProblemId();
        Problem problem = problemService.getById(problemId);
        if (problem == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "Problem not found");
        }
        // 2. Check the judging status of the submission, if the submission is not `pending`, then no need to execute the code again
        if (!problemSubmit.getStatus().equals(ProblemSubmitStatusEnum.PENDING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "The solution is under judging");
        }
        // 3. Update the judging status `status` from `pending` to `judging`, prevent from repeat execution
        ProblemSubmit problemSubmitUpdate = new ProblemSubmit();
        problemSubmitUpdate.setId(problemSubmitId);
        problemSubmitUpdate.setStatus(ProblemSubmitStatusEnum.JUDGING.getValue());
        boolean res = problemSubmitService.updateById(problemSubmitUpdate);
        if (!res) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Solution judging status update failed");
        }
        // 4. Call the code sandbox, get the execution output
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String language = problemSubmit.getLanguage();
        String code = problemSubmit.getCode();
        // Get the judge cases(input and output cases)
        String judgeCaseStr = problem.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();
        // 5. According to the execution result, update the judging status and information
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setProblem(problem);
        judgeContext.setProblemSubmit(problemSubmit);
        // Strategy Pattern to optimize the judging strategies
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        // 6. Update the judging result to database table
        problemSubmitUpdate = new ProblemSubmit();
        problemSubmitUpdate.setId(problemSubmitId);
        problemSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        problemSubmitUpdate.setStatus(ProblemSubmitStatusEnum.ACCEPTED.getValue());
        res = problemSubmitService.updateById(problemSubmitUpdate);
        if (!res) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Solution judging status update failed");
        }
        ProblemSubmit problemSubmitResult = problemSubmitService.getById(problemSubmitId);
        return problemSubmitResult;
    }
}
