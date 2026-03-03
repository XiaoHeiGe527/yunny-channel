package com.yunny.channel.common.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by fe
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInsuranceDTO {
     /**
      * 
      */
     private Integer id;
     /**
      * 承保日期
      */
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
      * 车型名称
      */
     private String carTypeCn;

     /**
      * 车型
      */
     private Integer carType;

     /**
      * 批次号
      */
     private String batchNo;

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
      * 创建时间
      */
     private LocalDateTime createTime;
     /**
      * 修改时间
      */
     private LocalDateTime updateTime;

     /**
      * 备注
      */
     private String remarks;
}