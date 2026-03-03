package com.yunny.channel.common.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.yunny.channel.common.interfaces.InsertGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import com.yunny.channel.common.page.PageParameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


/**
 * Created by Fe
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiclesOutwardCardQuery {


     /**
      * 开卡 开始时间
      */
     private String openDateDateStart;

     /**
      * 开卡 开始结束时间
      */
     private String openDateDateEnd;

     /**
      * 车牌号
      */
     private String carNumber;

     /**
      * 车型
      */
     private Integer carType;

     /**
      * 车辆客户名称
      */
     private String vehicleCustomersName;

     /**
      * 卡剩余天数，默认开卡都是15天
      */
     @NotNull(message = "卡剩余天数不可为空", groups = {InsertGroup.class})
     private Integer daysRemaining;


     private LocalDateTime openDate;


     /**
      * 是否提醒
      */
     private String isReminder;


     /**
      * 到期提醒（获取当前时间）
      */
     private String expiryAlert;


     /**
      * 已经过期了
      */
     private String expiredAlready;


     /**
      * 车辆使用状态 ：1 可用，2 外出中
      */
     private Integer activeState;
     /**
      * 卡状态: 0 失效卡 已使用的卡，1 可用卡 可发车 ，3，卡使用中
      */
     private Integer state;

     /**
      * 备注
      */
     private String remarks;


     private Integer currentPage;

     private Integer pageSize;

     private PageParameter pageParameter;

}
