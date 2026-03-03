package com.yunny.channel.common.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

/**
 * Created by fe
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiclesOutwardCardDTO {
     /**
      * 
      */
     private Long id;
     /**
      * 车卡 卡号
      */
     @NotNull(message = "车牌号不可为空", groups = {UpdateGroup.class})
     private String cardNo;
     /**
      * 车牌号
      */
     @NotNull(message = "车牌号不可为空", groups = {InsertGroup.class,UpdateGroup.class})
     private String carNumber;

     /**
      * 车辆客户名称
      */
     @NotNull(message = "车辆客户名称不可为空", groups = {InsertGroup.class})
     private String vehicleCustomersName;


     /**
      * 车辆客户表ID
      */
     private Integer vehicleCustomersId;
     /**
      * 卡剩余天数，默认开卡都是15天
      */
     //@NotNull(message = "卡剩余天数不可为空", groups = {InsertGroup.class})
     private Integer daysRemaining;
     /**
      * 开卡时间
      */
     @NotNull(message = "开卡时间不能为空", groups = {InsertGroup.class})
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime openDate;
     /**
      * 提醒用卡时间（一般都是开卡时间+剩余天数-5天）
      */
     private LocalDateTime warningTime;
     /**
      * 卡到期时间
      */
     @NotNull(message = "开卡时间不能为空", groups = {InsertGroup.class})
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime expirationDate;
     /**
      * 卡状态: 0 失效卡 已使用的卡，1 可用卡 可发车 ，2，到期未使用
      */
     private Integer state;
     /**
      * 备注
      */
     private String remarks;
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