package com.sun.overweight.common.utils;

/**
 * Created by bf on 2018/11/13.
 */
public class PageUtil {
    public static Integer getTotalPage(Integer totalSize,Integer pageSize){
        if(totalSize==0||pageSize==0){
            return 0;
        }
        if(totalSize<=pageSize){
            return 1;
        }
        int totalPage = 1;
        int remain = totalSize%pageSize;
        if(remain==0){
            totalPage = (totalSize-remain)/pageSize;
        }
        if(remain>0){
            totalPage = (totalSize-remain)/pageSize+1;
        }
        return totalPage;
    }
}
