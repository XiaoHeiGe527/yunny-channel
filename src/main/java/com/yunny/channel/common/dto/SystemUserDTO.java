package com.yunny.channel.common.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Created by fe
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemUserDTO {
     /**
      * 
      */
     private Long id;
     /**
      * 
      */
     @NotNull(message = "用户号不可为空", groups = {UpdateGroup.class})
     private String userNo;
     /**
      * 登陆名称
      */
     @NotNull(message = "登录账号不可为空", groups = {InsertGroup.class})
     private String loginName;
     /**
      * 姓名
      */
     @NotNull(message = "账号姓名不可为空", groups = {InsertGroup.class})

     private String name;
     /**
      * 
      */
     @NotNull(message = "密码不可为空", groups = {InsertGroup.class})
     private String password;


     /**
      * 
      */
     private String mobile;
     /**
      * 
      */
     private LocalDateTime createTime;
     /**
      * 
      */
     private LocalDateTime updateTime;
     /**
      * 状态 -0无效 -1有效
      */
     private Integer state;
     /**
      * 
      */
     private Integer age;
     /**
      * 
      */
     private String city;
     /**
      * 
      */
     private String email;


     @NotNull(message = "用户角色不可为空", groups = {InsertGroup.class})
     private Integer roleId;
}