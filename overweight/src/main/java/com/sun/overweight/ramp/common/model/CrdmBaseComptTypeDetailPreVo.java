package com.sun.overweight.ramp.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.List;

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
public class CrdmBaseComptTypeDetailPreVo {
    private String labelName;
    private String modLabelName;
    private String isShowed;
    private String isRequired;
    private String labelType;
    private List<CrdmBaseCompTypeVo> labelTypeList;
}