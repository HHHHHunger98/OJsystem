package com.oj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oj.model.dto.problemsubmit.ProblemSubmitQueryRequest;
import com.oj.model.dto.problemsubmit.ProblemSubmitAddRequest;
import com.oj.model.entity.ProblemSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oj.model.entity.User;
import com.oj.model.vo.ProblemSubmitVO;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * Get query conditions
     *
     * @param problemSubmitQueryRequest
     * @return
     */
    QueryWrapper<ProblemSubmit> getQueryWrapper(ProblemSubmitQueryRequest problemSubmitQueryRequest);

    /**
     * Get problem encapsulation
     *
     * @param problemSubmit
     * @param loginUser: current login user
     * @return
     */
    ProblemSubmitVO getProblemSubmitVO(ProblemSubmit problemSubmit, User loginUser);

    /**
     * Get problem encapsulation in paging
     *
     * @param problemSubmitPage
     * @param loginUser: current login user
     * @return
     */
    Page<ProblemSubmitVO> getProblemSubmitVOPage(Page<ProblemSubmit> problemSubmitPage, User loginUser);
}
