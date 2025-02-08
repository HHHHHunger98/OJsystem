package com.oj.model.dto.problemsubmit;

import lombok.Data;

import java.io.Serializable;

/**
 * Problem点赞请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class ProblemSubmitAddRequest implements Serializable {

    /**
     * problem id
     */
    private Long problemId;

    /**
     * solution language
     */
    private String language;

    /**
     * user solution
     */
    private String code;

    private static final long serialVersionUID = 1L;
}