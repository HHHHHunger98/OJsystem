package com.oj.model.dto.problemsubmit;

import com.oj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Problem submission query Request
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProblemSubmitQueryRequest extends PageRequest implements Serializable {

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
    private Integer status;

    /**
     * user id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}