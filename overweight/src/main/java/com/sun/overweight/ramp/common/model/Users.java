package com.sun.overweight.ramp.common.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.sql.Timestamp;

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
}
