package com.yunny.channel.common.query;

import com.yunny.channel.common.interfaces.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.yunny.channel.common.page.PageParameter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by Fe
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemUserQuery {

     /**
      * 登陆名称
      */
     private String loginName;
     /**
      *
      */
     private String name;

     private Integer state;

     private String mobile;

     @NotBlank(message = "旧密码不能为空", groups = {UpdateGroup.class})
     private String oldPassword;

     @NotBlank(message = "密码不能为空", groups = {UpdateGroup.class})
     private String password;

     @NotBlank(message = "用户编号不可为空", groups = {UpdateGroup.class})
     private String userNo;

     private Integer currentPage;

     private Integer pageSize;

     private PageParameter pageParameter;

}
