package com.sun.overweight.entity;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author sunwx33102
 * @description
 * @date 2022-04-25 11:13
 */
@Builder
@Data
@ToString
public class EasyExcelParams {

    private String fileName;

    private String sheetName;

    private Boolean needHead;

    private List data;
}