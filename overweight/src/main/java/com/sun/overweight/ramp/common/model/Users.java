package com.sun.overweight.ramp.common.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.sql.Timestamp;
import java.util.List;

/**
 * @date
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldNameConstants
public class Users {
    private String name;
    private String type;
    private String date;
    private List<String> scrNumList;
    private List<Integer> poolIdList;
    private Boolean workflowFlag;
    private String adjustModeType;
}
