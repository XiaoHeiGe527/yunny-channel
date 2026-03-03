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
public class SystemRoleResourceDO extends BaseDO{
     /**
      * 
      */
     private Integer id;
     /**
      * 
      */
     private Integer roleId;
     /**
      * 
      */
     private Integer resourceId;
}