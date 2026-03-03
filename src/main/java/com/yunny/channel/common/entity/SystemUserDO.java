package com.yunny.channel.common.entity;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by fe
 * 用户信息
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemUserDO extends BaseDO{
     /**
      * 
      */
     private Long id;
     /**
      * 
      */
     private String userNo;
     /**
      * 登陆名称
      */
     private String loginName;
     /**
      * 
      */
     private String name;
     /**
      * 
      */
     private String password;
     /**
      * 
      */
     private String mobile;
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
}