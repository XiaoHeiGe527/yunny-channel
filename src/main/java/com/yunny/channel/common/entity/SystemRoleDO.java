package com.yunny.channel.common.entity;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by fe
 * 系统角色表
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemRoleDO extends BaseDO{
     /**
      * 
      */
     private Integer id;
     /**
      * 角色名
      */
     private String name;
     /**
      * 角色说明
      */
     private String content;
     /**
      * 创建人
      */
     private String createMan;
     /**
      * 更新人
      */
     private String updateMan;
     /**
      * 0:管理员 1:普通
      */
     private Integer type;
     /**
      * 0:无效 1:有效
      */
     private Integer state;
}