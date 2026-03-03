package com.yunny.channel.common.query;

import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.SearchGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.yunny.channel.common.page.PageParameter;

import javax.validation.constraints.NotBlank;

/**
 * Created by Fe
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryQuery {

     /**
      * 类别（比如 中文的 部门 职级 岗位）
      */
     @NotBlank(message = "类别不能为空", groups = {SearchGroup.class})
     private String category;
     /**
      * 比如下拉但 高管对应的value是1，存1
      */
     private String codeNum;
     /**
      * 字典解释比如下拉单的中文显示部分
      */
     private String explain;

     /**
      * 判断不是当前ID的记录
      */
     private Long  notId;

     private String remarks;

     private Integer currentPage;

     private Integer pageSize;

     private PageParameter pageParameter;

}
