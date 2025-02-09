package com.oj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oj.common.BaseResponse;
import com.oj.common.ErrorCode;
import com.oj.common.ResultUtils;
import com.oj.exception.BusinessException;
import com.oj.model.dto.problemsubmit.ProblemSubmitQueryRequest;
import com.oj.model.dto.problemsubmit.ProblemSubmitAddRequest;
import com.oj.model.entity.ProblemSubmit;
import com.oj.model.entity.User;
import com.oj.model.vo.ProblemSubmitVO;
import com.oj.service.ProblemSubmitService;
import com.oj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * problem submit接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/problem_submit")
@Slf4j
public class ProblemSubmitController {

    @Resource
    private ProblemSubmitService problemSubmitService;

    @Resource
    private UserService userService;

    /**
     * Submit the solution
     *
     * @param problemSubmitAddRequest
     * @param request
     * @return problemSubmitId the submission id
     */
    @PostMapping("/")
    public BaseResponse<Long> doProblemSubmit(@RequestBody ProblemSubmitAddRequest problemSubmitAddRequest,
            HttpServletRequest request) {
        if (problemSubmitAddRequest == null || problemSubmitAddRequest.getProblemId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // Submission is allowed after a login success
        final User loginUser = userService.getLoginUser(request);
        long problemSubmitId = problemSubmitService.doProblemSubmit(problemSubmitAddRequest, loginUser);
        return ResultUtils.success(problemSubmitId);
    }

    /**
     * list problem submission by page
     * Note: except admin, normal user can only access non-solution part of the submission.
     * For instance, code,
     * @param problemSubmitQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<ProblemSubmitVO>> listProblemSubmitByPage(@RequestBody ProblemSubmitQueryRequest problemSubmitQueryRequest,
                                                                       HttpServletRequest request) {
        long current = problemSubmitQueryRequest.getCurrent();
        long size = problemSubmitQueryRequest.getPageSize();
        final User loginUser = userService.getLoginUser(request);
        // get problem submission by page from database
        Page<ProblemSubmit> problemSubmitPage = problemSubmitService.page(new Page<>(current, size),
                problemSubmitService.getQueryWrapper(problemSubmitQueryRequest));
        // Data masking before sending them to frontend
        return ResultUtils.success(problemSubmitService.getProblemSubmitVOPage(problemSubmitPage, loginUser));
    }
}
