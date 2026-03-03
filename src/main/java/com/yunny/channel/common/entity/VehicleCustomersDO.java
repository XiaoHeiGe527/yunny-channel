package com.yunny.channel.common.entity;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by fe
 * 车辆客户表
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleCustomersDO extends BaseDO{
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
}