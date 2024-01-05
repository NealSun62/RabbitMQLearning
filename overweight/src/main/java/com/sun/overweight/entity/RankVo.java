package com.sun.overweight.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author sunwx33102
 * @description
 * @date 2022-10-18 16:03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankVo {
    /**
     * 对象标记
     */
    private String code;
    /**
     * 得分
     */
    private BigDecimal value;
    /**
     * 原始得分
     */
    private BigDecimal oriValue;
    /**
     * 排名
     */
    private Integer rankNum;
}