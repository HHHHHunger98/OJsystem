package com.oj.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * problem
 * @TableName problem
 */
@TableName(value ="problem")
@Data
public class Problem {
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
    private String tags;

    /**
     * solution to the problem
     */
    private String solution;

    /**
     * total submit times
     */
    private Integer submitNum;

    /**
     * total accepted times
     */
    private Integer acceptNum;

    /**
     * input/output cases(json array)
     */
    private String judgeCase;

    /**
     * configuration for judging(json object)
     */
    private String judgeConfig;

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
     * is deleted
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}