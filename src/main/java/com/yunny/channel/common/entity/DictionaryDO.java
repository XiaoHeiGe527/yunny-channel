package com.yunny.channel.common.entity;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by fe
 * 字典表
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryDO extends BaseDO{
     /**
      * 
      */
     private Long id;
     /**
      * 类别（比如 中文的 部门 职级 岗位）
      */
     private String category;
     /**
      * 比如下拉但 高管对应的value是1，存1
      */
     private String codeNum;
     /**
      * 字典解释比如下拉单的中文显示部分
      */
     private String content;
     /**
      * 暂定
      */
     private String remarks;
     /**
      * 顺序号
      */
     private Integer serialNumber;
}