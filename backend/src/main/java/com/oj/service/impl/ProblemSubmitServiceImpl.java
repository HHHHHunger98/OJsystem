package com.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oj.common.ErrorCode;
import com.oj.exception.BusinessException;
import com.oj.model.dto.problemsubmit.ProblemSubmitAddRequest;
import com.oj.model.entity.*;
import com.oj.model.enums.ProblemSubmitLanguageEnum;
import com.oj.model.enums.ProblemSubmitStatusEnum;
import com.oj.service.ProblemService;
import com.oj.service.ProblemSubmitService;
import com.oj.mapper.ProblemSubmitMapper;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

/**
* @author wzh
* @description 针对表【problem_submit(problem submission)】的数据库操作Service实现
* @createDate 2025-02-07 13:41:24
*/
@Service
public class ProblemSubmitServiceImpl extends ServiceImpl<ProblemSubmitMapper, ProblemSubmit>
    implements ProblemSubmitService{
    @Resource
    private ProblemService problemService;

    /**
     * problem submission
     *
     * @param problemSubmitAddRequest the request of problem solution submission
     * @param loginUser
     * @return
     */
    @Override
    public long doProblemSubmit(ProblemSubmitAddRequest problemSubmitAddRequest, User loginUser) {
        // todo check if the submission language is valid
        String language = problemSubmitAddRequest.getLanguage();
        ProblemSubmitLanguageEnum problemSubmitLanguageEnum = ProblemSubmitLanguageEnum.getEnumByValue(language);
        if (problemSubmitLanguageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "submission language error");
        }

        long problemId = problemSubmitAddRequest.getProblemId();
        // the entity must exist and obtain the entity according to the class
        Problem problem = problemService.getById(problemId);
        if (problem == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // Whether the user already submitted the solution to the problem
        long userId = loginUser.getId();
        ProblemSubmit problemSubmit = new ProblemSubmit();
        problemSubmit.setUserId(userId);
        problemSubmit.setProblemId(problemId);
        problemSubmit.setCode(problemSubmitAddRequest.getCode());
        problemSubmit.setLanguage(language);
        // Set the initial status
        problemSubmit.setStatus(ProblemSubmitStatusEnum.PENDING.getValue());
        problemSubmit.setJudgeInfo("{}");
        boolean save = this.save(problemSubmit);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Problem submit failed");
        }
        return problemSubmit.getId();


//        /** @todo refine the submission process
//         * Each user should do the problem submission in a serial manner
//         * Use the lock to wrap the transaction method, because in the worst case, user can click submit 10
//         * times with the same code in a short period, in order to reduce the waste of computation resource, we should
//         * use a lock for preventing that happens
//         */
//        ProblemSubmitService problemSubmitService = (ProblemSubmitService) AopContext.currentProxy();
//        synchronized (String.valueOf(userId).intern()) {
//            return problemSubmitService.doProblemSubmitInner(userId, problemId);
//        }
    }
}




