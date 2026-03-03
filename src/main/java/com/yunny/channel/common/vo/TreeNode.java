package com.yunny.channel.common.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 树形结构
 */
@Setter
@Getter
public class TreeNode implements Serializable {

    @JSONField(ordinal = 1)
    private String dm;

    @JSONField(ordinal = 2)
    private String mc;

    @JSONField(ordinal = 3)
    private String jc;

    @JSONField(ordinal = 4)
    private String sjdm;

    @JSONField(ordinal = 6)
    private Integer xh;

    @JSONField(ordinal = 5)
    private List<TreeNode> children;

    private String dqdm;

    private String fjdqdm;
}
