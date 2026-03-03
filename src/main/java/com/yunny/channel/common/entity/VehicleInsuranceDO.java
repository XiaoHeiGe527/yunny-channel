package com.yunny.channel.common.entity;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by fe
 * 车辆承保日期表
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInsuranceDO extends BaseDO{
     /**
      * 
      */
     private Long id;
     /**
      * 承保日期
      */
     private LocalDateTime underwritingDate;
     /**
      * 投保人
      */
     private String insured;
     /**
      * 车牌号
      */
     private String carNumber;

     /**
      * 批次号
      */
     private String batchNo;

     /**
      * 车型名称
      */
     private String carTypeCn;

     /**
      * 车型
      */
     private Integer carType;

     /**
      * 交强险
      */
     private String compulsoryInsurance;
     /**
      * 车船税
      */
     private String vehicleAndVesselTax;
     /**
      * 商业税
      */
     private String businessTax;
     /**
      * 非车险
      */
     private String nonMotorInsurance;
     /**
      * 共计
      */
     private String total;
     /**
      * 承保日期提醒时间
      */
     private LocalDateTime policyExpiryAlert;

     /**
      * 备注
      */
     private String remarks;

     /**
      * 数据操作人
      */
     private String  updateMan;

}