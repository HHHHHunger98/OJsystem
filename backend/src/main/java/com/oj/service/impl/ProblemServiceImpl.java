package com.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.oj.common.ErrorCode;
import com.oj.constant.CommonConstant;
import com.oj.exception.BusinessException;
import com.oj.exception.ThrowUtils;
import com.oj.model.dto.problem.ProblemQueryRequest;
import com.oj.model.entity.*;
import com.oj.model.vo.ProblemVO;
import com.oj.model.vo.UserVO;
import com.oj.service.ProblemService;
import com.oj.mapper.ProblemMapper;
import com.oj.service.UserService;
import com.oj.utils.SqlUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author wzh
* @description 针对表【problem(problem)】的数据库操作Service实现
* @createDate 2025-02-07 13:38:36
*/
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem>
    implements ProblemService{
    private final static Gson GSON = new Gson();

    @Resource
    private UserService userService;

    /**
     * Validate the problem
     * @param problem
     * @param add
     */
    @Override
    public void validProblem(Problem problem, boolean add) {
        if (problem == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String title = problem.getTitle();
        String content = problem.getContent();
        String tags = problem.getTags();
        String solution = problem.getSolution();
        String judgeCase = problem.getJudgeCase();
        String judgeConfig = problem.getJudgeConfig();

        // Validation for creating a new problem without additional parameters, the parameters title, content and tags should not be empty
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(title, content, tags), ErrorCode.PARAMS_ERROR);
        }
        // Validation for creating with additional parameters
        if (StringUtils.isNotBlank(title) && title.length() > 80) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "the title is too long");
        }
        if (StringUtils.isNotBlank(content) && content.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "the content is too long");
        }
        if (StringUtils.isNotBlank(solution) && solution.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "the solution is too long");
        }
        /**
         * @todo more complexer validation of judgeCase and judgeConfig
         */
        if (StringUtils.isNotBlank(judgeCase) && judgeCase.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "the judge case is too long");
        }
        if (StringUtils.isNotBlank(judgeConfig) && judgeConfig.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "the judge configuration is too long");
        }
    }

    /**
     * Get the query wrapper class: fields can be used by users' query
     * Based on the client request, return the corresponding query wrapper object(based on mybatis framework)
     * @param problemQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Problem> getQueryWrapper(ProblemQueryRequest problemQueryRequest) {
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        if (problemQueryRequest == null) {
            return queryWrapper;
        }

        Long id = problemQueryRequest.getId();
        String title = problemQueryRequest.getTitle();
        String content = problemQueryRequest.getContent();
        List<String> tags = problemQueryRequest.getTags();
        String solution = problemQueryRequest.getSolution();
        Long userId = problemQueryRequest.getUserId();
        String sortField = problemQueryRequest.getSortField();
        String sortOrder = problemQueryRequest.getSortOrder();

        // Concatenate query conditions
        queryWrapper.like(StringUtils.isNotBlank(title), "title", title);
        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        queryWrapper.like(StringUtils.isNotBlank(solution), "solution", solution);
        if (CollectionUtils.isNotEmpty(tags)) {
            for (String tag : tags) {
                queryWrapper.like("tags", "\"" + tag + "\"");
            }
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public ProblemVO getProblemVO(Problem problem, HttpServletRequest request) {
        ProblemVO problemVO = ProblemVO.objToVo(problem);
        long problemId = problem.getId();
        // 1. Join Select of user info
        Long userId = problem.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        problemVO.setUserVO(userVO);
        return problemVO;
    }

    @Override
    public Page<ProblemVO> getProblemVOPage(Page<Problem> problemPage, HttpServletRequest request) {
        List<Problem> problemList = problemPage.getRecords();
        Page<ProblemVO> problemVOPage = new Page<>(problemPage.getCurrent(), problemPage.getSize(), problemPage.getTotal());
        if (CollectionUtils.isEmpty(problemList)) {
            return problemVOPage;
        }
        // JOIN Select of user info
        Set<Long> userIdSet = problemList.stream().map(Problem::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));

        // Additional info
        List<ProblemVO> problemVOList = problemList.stream().map(problem -> {
            ProblemVO problemVO = ProblemVO.objToVo(problem);
            Long userId = problem.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            problemVO.setUserVO(userService.getUserVO(user));
            return problemVO;
        }).collect(Collectors.toList());
        problemVOPage.setRecords(problemVOList);
        return problemVOPage;
    }
}




