package com.yunny.channel.common.entity;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by fe
 * 
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemUserRoleDO extends BaseDO{
     /**
      * 
      */
     private Integer id;
     /**
      * 
      */
     private String userNo;
     /**
      * 角色id
      */
     private Integer roleId;
}