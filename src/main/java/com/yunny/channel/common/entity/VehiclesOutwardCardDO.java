package com.yunny.channel.common.entity;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by fe
 * 公司车卡表（车队发车用的）
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiclesOutwardCardDO extends BaseDO{
     /**
      * 
      */
     private Long id;
     /**
      * 车卡 卡号
      */
     private String cardNo;
     /**
      * 车牌号
      */
     private String carNumber;


     /**
      * 客户名称
      */
     private String vehicleCustomersName;


     /**
      * 非此表的
      * 车型:1 特三,2丰田,3 希尔,4五菱,5春星,6货,7长城货,8长城,9 红宇,10 鸿星达,11 哈弗,12 霸道,13 雷克萨斯,14 雪佛兰,15 奔驰,16 江特,17 威尔法,18 奥迪,19 比亚迪,20 酷路泽,21 普拉多
      */
     private Integer carType;

     /**
      * 车辆客户表ID
      */
     private Integer vehicleCustomersId;
     /**
      * 卡剩余天数，默认开卡都是15天
      */
     private Integer daysRemaining;
     /**
      * 开卡时间
      */
     private LocalDateTime openDate;
     /**
      * 提醒用卡时间（一般都是开卡时间+剩余天数-5天）
      */
     private LocalDateTime warningTime;
     /**
      * 卡到期时间
      */
     private LocalDateTime expirationDate;
     /**
      * 卡状态: 0 失效卡 已使用的卡，1 可用卡 可发车 ，3，卡使用中
      */
     private Integer state;

     /**
      * 车辆使用状态 ：1 可用，2 外出中
      */
     private Integer activeState;

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
}