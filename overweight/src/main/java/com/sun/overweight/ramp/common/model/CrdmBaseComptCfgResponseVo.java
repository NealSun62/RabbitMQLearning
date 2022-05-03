package com.sun.overweight.ramp.common.model;

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
public class CrdmBaseComptCfgResponseVo {
    private String uuid;
    private String companyId;
    private String processDbId;
    private String processDbName;
    private String nodeName;
    private String comptValue;
}