package com.oj.model.vo;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.google.gson.reflect.TypeToken;
import com.oj.model.dto.problem.JudgeConfig;
import com.oj.model.entity.Problem;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

/**
 * problem
 * @TableName problem
 */
@Data
public class ProblemVO {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * title
     */
    private String title;

    /**
     * content
     */
    private String content;

    /**
     * tags array(json array)
     */
    private List<String> tags;

    /**
     * total submit times
     */
    private Integer submitNum;

    /**
     * total accepted times
     */
    private Integer acceptNum;

    /**
     * configuration for judging(json object)
     */
    private JudgeConfig judgeConfig;

    /**
     * number of likes
     */
    private Integer thumbNum;

    /**
     * number of add to favorite
     */
    private Integer favourNum;

    /**
     * created user id
     */
    private Long userId;

    /**
     * created at
     */
    private Date createTime;

    /**
     * time updated
     */
    private Date updateTime;

    /**
     * the creator info: who has created this problem
     */
    private UserVO userVO;

    /**
     * Wrapper class to object
     * VO object to problem object
     * @param problemVO
     * @return
     */
    public static Problem voToObj(ProblemVO problemVO) {
        if (problemVO == null) {
            return null;
        }
        Problem problem = new Problem();
        BeanUtils.copyProperties(problemVO, problem);
        List<String> tagList = problemVO.getTags();
        if (tagList != null) {
            problem.setTags(JSONUtil.toJsonStr(tagList));
        }
        JudgeConfig judgeConfigVO = problemVO.getJudgeConfig();
        if (judgeConfigVO != null) {
            problem.setJudgeConfig(JSONUtil.toJsonStr(judgeConfigVO));
        }
        return problem;
    }
    
    /**
     * object to Wrapper class
     * Problem object to VO object
     * @param problem
     * @return
     */
    public static ProblemVO objToVo(Problem problem) {
        if (problem == null) {
            return null;
        }
        ProblemVO problemVO = new ProblemVO();
        BeanUtils.copyProperties(problem, problemVO);
        List<String> tagList = JSONUtil.toList(problem.getTags(), String.class);
        problemVO.setTags(tagList);
        String judgeConfigStr = problem.getJudgeConfig();
        problemVO.setJudgeConfig(JSONUtil.toBean(judgeConfigStr, JudgeConfig.class));
        return problemVO;
    }

    private static final long serialVersionUID = 1L;
}