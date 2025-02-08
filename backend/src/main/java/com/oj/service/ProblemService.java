package com.oj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oj.model.dto.problem.ProblemQueryRequest;
import com.oj.model.entity.Problem;
import com.oj.model.entity.Problem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oj.model.vo.ProblemVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author wzh
* @description for database operations on the problem table, Service Layer
* @createDate 2025-02-07 13:38:36
*/
public interface ProblemService extends IService<Problem> {
    /**
     * validation
     *
     * @param problem
     * @param add
     */
    void validProblem(Problem problem, boolean add);

    /**
     * Get query conditions
     *
     * @param problemQueryRequest
     * @return
     */
    QueryWrapper<Problem> getQueryWrapper(ProblemQueryRequest problemQueryRequest);
    
    /**
     * Get problem encapsulation
     *
     * @param problem
     * @param request
     * @return
     */
    ProblemVO getProblemVO(Problem problem, HttpServletRequest request);

    /**
     * Get problem encapsulation in paging
     *
     * @param problemPage
     * @param request
     * @return
     */
    Page<ProblemVO> getProblemVOPage(Page<Problem> problemPage, HttpServletRequest request);
}
