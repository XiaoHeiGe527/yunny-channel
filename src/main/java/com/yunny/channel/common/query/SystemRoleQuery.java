package com.yunny.channel.common.query;

import com.yunny.channel.common.page.PageParameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Fe
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemRoleQuery {

     /**
      * 角色名
      */
     private String name;

     /**
      * 0:无效 1:有效
      */
     private Integer state;

     private Integer currentPage;

     private Integer pageSize;

     private PageParameter pageParameter;

}
