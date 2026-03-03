package com.yunny.channel.common.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Created by fe
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryDTO {
     /**
      * 
      */
     private Long id;
     /**
      * 类别（比如 中文的 部门 职级 岗位）
      */
     @NotBlank(message = "类别不能为空", groups = {InsertGroup.class, UpdateGroup.class})
     private String category;
     /**
      * 比如下拉但 高管对应的value是1，存1
      */
     @NotBlank(message = "字典编码不能为空", groups = {InsertGroup.class, UpdateGroup.class})
     private String codeNum;
     /**
      * 字典解释比如下拉单的中文显示部分
      */
     @NotBlank(message = "字典内容不能为空", groups = {InsertGroup.class, UpdateGroup.class})
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