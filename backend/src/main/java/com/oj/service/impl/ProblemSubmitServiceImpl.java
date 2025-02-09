package com.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oj.common.ErrorCode;
import com.oj.constant.CommonConstant;
import com.oj.exception.BusinessException;
import com.oj.model.dto.problemsubmit.ProblemSubmitQueryRequest;
import com.oj.model.dto.problemsubmit.ProblemSubmitAddRequest;
import com.oj.model.entity.*;
import com.oj.model.enums.ProblemSubmitLanguageEnum;
import com.oj.model.enums.ProblemSubmitStatusEnum;
import com.oj.model.vo.ProblemSubmitVO;
import com.oj.service.ProblemService;
import com.oj.service.ProblemSubmitService;
import com.oj.mapper.ProblemSubmitMapper;
import com.oj.service.UserService;
import com.oj.utils.SqlUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Resource
    private UserService userService;

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
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "The submission language is invalid");
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


        /** @todo refine the submission process
         * Each user should do the problem submission in a serial manner
         * Use the lock to wrap the transaction method, because in the worst case, user can click submit 10
         * times with the same code in a short period, in order to reduce the waste of computation resource, we should
         * use a lock for preventing that happens
         */
//        ProblemSubmitService problemSubmitService = (ProblemSubmitService) AopContext.currentProxy();
//        synchronized (String.valueOf(userId).intern()) {
//            return problemSubmitService.doProblemSubmitInner(userId, problemId);
//        }
    }

    /**
     * Generate a query for the problem submit
     *
     * @param problemSubmitQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<ProblemSubmit> getQueryWrapper(ProblemSubmitQueryRequest problemSubmitQueryRequest) {
        QueryWrapper<ProblemSubmit> queryWrapper = new QueryWrapper<>();
        if (problemSubmitQueryRequest == null) {
            return queryWrapper;
        }
        Long problemId = problemSubmitQueryRequest.getProblemId();
        String language = problemSubmitQueryRequest.getLanguage();
        Integer status = problemSubmitQueryRequest.getStatus();
        Long userId = problemSubmitQueryRequest.getUserId();
        String sortField = problemSubmitQueryRequest.getSortField();
        String sortOrder = problemSubmitQueryRequest.getSortOrder();

        // Concatenate query conditions
        queryWrapper.eq(StringUtils.isNotBlank(language),"language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(problemId),"problemId", problemId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ProblemSubmitStatusEnum.getEnumByValue(status) != null, "status", status);
        queryWrapper.eq("isDelete", false);

        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public ProblemSubmitVO getProblemSubmitVO(ProblemSubmit problemSubmit, User loginUser) {
        ProblemSubmitVO problemSubmitVO = ProblemSubmitVO.objToVo(problemSubmit);

        // data masking: only the current user and the admin can access the solution(submitted code)
        long userId = loginUser.getId();
        // data masking
        if (userId != problemSubmit.getUserId() && !userService.isAdmin(loginUser)) {
            problemSubmitVO.setCode(null);
        }
        return problemSubmitVO;
    }

    @Override
    public Page<ProblemSubmitVO> getProblemSubmitVOPage(Page<ProblemSubmit> problemSubmitPage, User loginUser) {
        List<ProblemSubmit> problemSubmitList = problemSubmitPage.getRecords();
        Page<ProblemSubmitVO> problemSubmitVOPage = new Page<>(problemSubmitPage.getCurrent(), problemSubmitPage.getSize(), problemSubmitPage.getTotal());
        if (CollectionUtils.isEmpty(problemSubmitList)) {
            return problemSubmitVOPage;
        }
        List<ProblemSubmitVO> problemSubmitVOList = problemSubmitList.stream()
                .map(problemSubmit -> getProblemSubmitVO(problemSubmit, loginUser))
                .collect(Collectors.toList());
        problemSubmitVOPage.setRecords(problemSubmitVOList);
        return problemSubmitVOPage;
    }
}




