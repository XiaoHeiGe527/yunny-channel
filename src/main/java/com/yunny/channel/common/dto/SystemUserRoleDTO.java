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
public class SystemUserRoleDTO {
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