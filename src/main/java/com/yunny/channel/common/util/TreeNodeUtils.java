package com.yunny.channel.common.util;

import com.yunny.channel.common.vo.TreeNode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 树形工具类
 */
@Setter
@Getter
public class TreeNodeUtils {

    /**
     * 全部节点
     */
    private List<TreeNode> allNodes;

    /**
     * 全部节点
     */
    private Map<String,TreeNode> mapNodes;

    private TreeNodeUtils(){
        allNodes = new ArrayList<>();
        mapNodes = new HashMap<>();
    }


    /**
     * 获取实例对象
     * @return
     */
    public static TreeNodeUtils builder(){
        return new TreeNodeUtils();
    }

    /**
     * 添加节点
     * @param treeNode
     */
    public void addNode(TreeNode treeNode){
        allNodes.add(treeNode);
        mapNodes.put(treeNode.getDqdm(),treeNode);
    }


    /**
     * 添加子节点
     * @param parentId
     * @param currNode
     */
    private void addToChildNode(String parentId,TreeNode currNode){
        TreeNode parentNode = mapNodes.get(parentId);
        if(parentNode != null){
            List<TreeNode> children = parentNode.getChildren();
            if(children == null){
                children = new ArrayList<>();
            }
            children.add(currNode);
            parentNode.setChildren(children);
        }
    }


    /**
     * 释放数据
     */
    private void release(){
        try {
            if(allNodes != null){
                allNodes.clear();
            }
        }finally {
            allNodes = null;
        }
        try {
            if(mapNodes != null){
                mapNodes.clear();
            }
        }finally {
            mapNodes = null;
        }
    }

    /**
     * 构建树形结构
     */
    public List<TreeNode> build(final String rootId){
        try {
            List<TreeNode> resultNodes = new ArrayList<>();
            allNodes.forEach(e -> {
                String parentId =  e.getFjdqdm();
                // 是否顶级节点
                if(isRootNode(parentId,rootId)){
                    resultNodes.add(e);
                }else{
                    addToChildNode(parentId,e);
                }
            });
            return  resultNodes;
        } finally {
            release();
        }
    }

    /**
     * 判定顶级节点
     * @param parentId 查询的数据父节点
     * @param rootId 指定的父级节点
     * @return
     */
    private static boolean isRootNode(String parentId,final String rootId){
       // if(parentId == null || "{}".equals(parentId) ||"0".equals(parentId)){
        if(parentId==null || rootId.equals(parentId)){
            return true;
        }
        return false;
    }
}
