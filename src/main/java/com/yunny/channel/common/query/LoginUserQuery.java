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
public class LoginUserQuery {

     private Long id;

     private String userNo;

     private String mobile;

     private Integer status;
     /**
      * 登录账号
      */
     private String loginName;

     /**
      * 密码
      */
     private String password;

     private Integer currentPage;

     private Integer pageSize;

     private PageParameter pageParameter;

}
