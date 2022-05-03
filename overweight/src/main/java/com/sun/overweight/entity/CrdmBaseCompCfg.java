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
public class CrdmBaseCompCfg {
    private String areaName;
    private String lablName;
    private String modlablName;
    private String isDisp;
    private String isRequ;
    private String lablType;
    private String lablCfgNo;
}