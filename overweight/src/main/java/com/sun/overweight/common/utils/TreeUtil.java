package com.sun.overweight.common.utils;

import com.sun.overweight.ramp.common.model.TreeNode;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 树工具类
 * @author 1
 */
public class TreeUtil {
    /**
     * 构建树
     *
     * @param list
     * @param idName
     * @param pidName
     * @param titleName
     * @param root
     * @param <T>
     * @return
     */
    public static <T> List<TreeNode<T>> buildTree(List<T> list, String idName, String pidName, String titleName, String root) {
        List<TreeNode<T>> treeNodes = new ArrayList<>();
        List<TreeNode<T>> rootNodes = new ArrayList<>();
        buildTreeNodes(list, idName, pidName, titleName, root, treeNodes, rootNodes);
        Map<String, List<TreeNode<T>>> pidGroup = treeNodes.stream().collect(Collectors.groupingBy(TreeNode::getPid));
        Iterator<TreeNode<T>> it = rootNodes.iterator();
        while (it.hasNext()) {
            setChildNodes(pidGroup, it.next());
        }
        return rootNodes;
    }

    /**
     * 将List构建成树节点的样式(多个节点)
     *
     * @param list
     * @param idName
     * @param pidName
     * @param titleName
     * @param <T>
     * @return
     */
    private static <T> List<TreeNode<T>> buildTreeNodes(List<T> list, String idName, String pidName, String titleName, String root, List<TreeNode<T>> treeNodes, List<TreeNode<T>> rootNodes) {
        // 将list构建成树节点的样式
        TreeNode treeNode = null;
        for (T item : list) {
            treeNode = buildTreeNode(item, idName, pidName, titleName);
            treeNodes.add(treeNode);
            if (isRoot(treeNode, root)) {
                rootNodes.add(treeNode);
            }
        }
        return treeNodes;
    }

    /**
     * 设置所有子节点
     *
     * @param pidGroupt
     * @param parentNode
     * @param <T>
     */
    private static <T> void setChildNodes(Map<String, List<TreeNode<T>>> pidGroupt, TreeNode<T> parentNode) {
        // 获取父节点直接下挂的所有子节点
        List<TreeNode<T>> children = pidGroupt.get(parentNode.getId());
        if (CollectionUtils.isNotEmpty(children)) {
            // 迭代每个子节点，为每个子节点设置它们直接下挂的所有子节点
            Iterator<TreeNode<T>> iterator = children.iterator();
            while (iterator.hasNext()) {
                setChildNodes(pidGroupt, iterator.next());
            }
            parentNode.setChildren(children);
        }
    }

    /**
     * 将对象构建成树节点的样式(单个节点)
     *
     * @param obj
     * @param idName
     * @param pidName
     * @param titleName
     * @return
     */
    private static TreeNode buildTreeNode(Object obj, String idName, String pidName, String titleName) {
        // 获取对象属性值
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        TreeNode treeNode = new TreeNode();
        treeNode.setId((String) beanWrapper.getPropertyValue(idName));
        treeNode.setPid((String) beanWrapper.getPropertyValue(pidName));
        treeNode.setTitle((String) beanWrapper.getPropertyValue(titleName));
        treeNode.setData(obj);
        return treeNode;
    }

    /**
     * 判断当前对象是否为根节点
     *
     * @param treeNode
     * @param root
     * @return
     */
    private static boolean isRoot(TreeNode treeNode, String root) {
        return treeNode.getPid() == null || treeNode.getPid().equals(root);
    }

}
