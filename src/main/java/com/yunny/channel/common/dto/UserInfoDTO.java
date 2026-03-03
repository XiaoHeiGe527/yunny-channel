package com.yunny.channel.common.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by fe
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
     /**
      * 
      */
     private Long id;
     /**
      * 用户编号
      */
     private String userNo;
     /**
      * 用户名称
      */
     private String userName;
     /**
      * 用户昵称
      */
     private String userNickName;
     /**
      * 用户密码
      */
     private String password;
     /**
      * 用户手机号
      */
     private String mobile;
     /**
      * 用户图像
      */
     private String userPicture;
     /**
      * 用户邮箱
      */
     private String userEmail;
     /**
      * 创建时间
      */
     private LocalDateTime createTime;
     /**
      * 修改时间
      */
     private LocalDateTime updateTime;
     /**
      * 用户状态 1有效 0无效
      */
     private Integer state;
     /**
      * 性别 0 男 1 女 2 待定
      */
     private Integer sex;
}