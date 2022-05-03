package com.sun.overweight.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * @author sunwx33102
 * @description
 * @date 2021-08-18 15:10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class CrdmBaseCompType {
    private String lablCfgNo;
    private String lablTypeCode;
    private String lablTypeVal;
}