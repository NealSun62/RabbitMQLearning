package com.sun.overweight.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author sunwx33102
 * @description
 * @date 2022-04-25 13:15
 */
@Builder
@Data
public class FormTemplate {

    private Long id;
    private String formTitle;
    private String formName;
    private Boolean isBlank;
    private String dataType;
    private String dataNum;

    private String valueType;
    private String defaultText;

    private String defaultPrompt;
    private String formSort;

    private String remarks;


}

