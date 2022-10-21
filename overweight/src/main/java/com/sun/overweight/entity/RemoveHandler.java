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
public class RemoveHandler implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主体编码
     */
    private String insNum;

    /**
     * 指标值
     */
    private BigDecimal value;
}