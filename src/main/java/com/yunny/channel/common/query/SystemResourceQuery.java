package com.yunny.channel.common.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.yunny.channel.common.page.PageParameter;

/**
 * Created by Fe
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemResourceQuery {

     private Integer roleId;

     /**
      * 权限名称
      */
     private String name;
     /**
      * 权限路径
      */
     private String url;
     /**
      * 0:无效 1:有效
      */
     private Integer state;


     private Integer currentPage;

     private Integer pageSize;

     private PageParameter pageParameter;

}
