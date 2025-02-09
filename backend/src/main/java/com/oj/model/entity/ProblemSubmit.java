package com.oj.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * problem submission
 * @TableName problem_submit
 */
@TableName(value ="problem_submit")
@Data
public class ProblemSubmit {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
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
    private String judgeInfo;

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
     * is deleted
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}