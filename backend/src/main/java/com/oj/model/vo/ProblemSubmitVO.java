package com.oj.model.vo;

import cn.hutool.json.JSONUtil;

import com.oj.model.dto.problemsubmit.JudgeInfo;
import com.oj.model.entity.ProblemSubmit;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * problem submit
 * @TableName problem submit
 */
@Data
public class ProblemSubmitVO {
    /**
     * id
     */
    private Long id;

    /**
     * problem id
     */
    private Long problemId;

    /**
     * user id
     */
    private Long userId;

    /**
     * solution language
     */
    private String language;

    /**
     * user solution
     */
    private String code;

    /**
     * information for judging(json object)
     */
    private JudgeInfo judgeInfo;

    /**
     * judging status(0-pending, 1-judging, 2-accepted, 3-failed)
     */
    private Integer status;

    /**
     * created at
     */
    private Date createTime;

    /**
     * time updated
     */
    private Date updateTime;

    /**
     * user info of the submission
     */
    private UserVO userVO;

    /**
     * problem of submission
     */
    private ProblemVO problemVO;

    /**
     * Wrapper class to object
     * VO object to problem submit object
     * @param problemSubmitVO
     * @return
     */
    public static ProblemSubmit voToObj(ProblemSubmitVO problemSubmitVO) {
        if (problemSubmitVO == null) {
            return null;
        }
        ProblemSubmit problemSubmit = new ProblemSubmit();
        BeanUtils.copyProperties(problemSubmitVO, problemSubmit);
        JudgeInfo judgeInfoObj = problemSubmitVO.getJudgeInfo();
        if (judgeInfoObj != null) {
            problemSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfoObj));
        }
        return problemSubmit;
    }
    
    /**
     * object to Wrapper class
     * ProblemSubmit object to VO object
     * @param problemSubmit
     * @return
     */
    public static ProblemSubmitVO objToVo(ProblemSubmit problemSubmit) {
        if (problemSubmit == null) {
            return null;
        }
        ProblemSubmitVO problemSubmitVO = new ProblemSubmitVO();
        BeanUtils.copyProperties(problemSubmit, problemSubmitVO);
        String judgeInfoStr = problemSubmit.getJudgeInfo();
        problemSubmitVO.setJudgeInfo(JSONUtil.toBean(judgeInfoStr, JudgeInfo.class));
        return problemSubmitVO;
    }

    private static final long serialVersionUID = 1L;
}