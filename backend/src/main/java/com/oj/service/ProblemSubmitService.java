package com.oj.service;

import com.oj.model.dto.problemsubmit.ProblemSubmitAddRequest;
import com.oj.model.entity.ProblemSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oj.model.entity.User;

/**
* @author wzh
* @description database operations on the problem_submit table, Service Layer
* @createDate 2025-02-07 13:41:24
*/
public interface ProblemSubmitService extends IService<ProblemSubmit> {
    /**
     * problem submission
     *
     * @param problemSubmitAddRequest
     * @param loginUser
     * @return problemSubmitId
     */
    long doProblemSubmit(ProblemSubmitAddRequest problemSubmitAddRequest, User loginUser);
}
