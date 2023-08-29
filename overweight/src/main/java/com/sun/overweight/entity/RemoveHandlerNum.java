package com.sun.overweight.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 功能说明:
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldNameConstants
public class RemoveHandlerNum implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主体编码
     */
    private int pos;

    /**
     * 指标值
     */
    private BigDecimal value;

    /**
     * 原始指标值
     */
    private BigDecimal oriValue;

    /**
     * 指标值
     */
    private Integer rankNum;
}