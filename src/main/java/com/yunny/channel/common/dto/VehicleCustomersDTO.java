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
public class VehicleCustomersDTO {
     /**
      * 
      */
     private Integer id;
     /**
      * 客户名称
      */
     private String name;
     /**
      * 客户地址
      */
     private String address;
     /**
      * 规格 Ф
      */
     private String specs;
     /**
      * 备注
      */
     private String remark;
     /**
      * 客户电话号
      */
     private String phoneNumber;
     /**
      * 序号
      */
     private Integer serialNumber;
     /**
      * 处理人
      */
     private String updateMan;
     /**
      * 修改时间
      */
     private LocalDateTime updateTime;
     /**
      * 创建时间
      */
     private LocalDateTime createTime;
}