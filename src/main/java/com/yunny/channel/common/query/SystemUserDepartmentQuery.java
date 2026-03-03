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
public class SystemUserDepartmentQuery {

     /**
      *
      */
     private Integer id;

     /**
      * 用户ID（关联system_user.id）
      */
     private String userNo;
     /**
      * 部门编码（关联dictionary.code_num，且dictionary.category="部门"）
      */
     private String deptCode;
     /**
      * 是否主部门：1-是，0-否（解决用户多部门时的“主归属”问题）
      */
     private Integer isMainDept;

     private String loginName;
     /**
      *
      */
     private String userName;


     private String deptName;

     private Integer currentPage;

     private Integer pageSize;

     private PageParameter pageParameter;

}
