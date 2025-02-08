package com.oj.model.dto.problem;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 更新请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class ProblemUpdateRequest implements Serializable {

    /**
     * id
     */
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
     * solution to the problem
     */
    private String solution;

    /**
     * input/output cases(json array)
     */
    private List<JudgeCase> judgeCase;

    /**
     * configuration for judging(json object)
     */
    private JudgeConfig judgeConfig;

    private static final long serialVersionUID = 1L;
}