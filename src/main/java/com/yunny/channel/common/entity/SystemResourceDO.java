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
public class SystemResourceDO extends BaseDO{
     /**
      * 
      */
     private Integer id;
     /**
      * 权限名称
      */
     private String name;
     /**
      * 权限路径
      */
     private String url;
     /**
      * 0:无效 1:有效
      */
     private Integer state;
     /**
      * 父类id
      */
     private Integer parentId;

     private Integer orderNum;
}