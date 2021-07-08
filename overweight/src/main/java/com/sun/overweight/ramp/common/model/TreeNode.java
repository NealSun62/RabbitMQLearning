package com.sun.overweight.ramp.common.model;

import lombok.Data;

import java.util.List;


/**
 * 树节点
 *
 */
@Data
public class TreeNode<T> {
    /**
     * 节点编号
     */
    private String id;
    /**
     * 父节点编号
     */
    private String pid;
    /**
     * 节点名称
     */
    private String title;
    /**
     * 数据
     */
    private T data;
    /**
     * 子节点
     */
    private List<TreeNode<T>> children;
}
