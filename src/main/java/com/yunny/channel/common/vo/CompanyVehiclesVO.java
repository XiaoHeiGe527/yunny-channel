package com.yunny.channel.common.vo;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

/**
 * Created by fe
 * 公司车辆表
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyVehiclesVO {
     /**
      * 
      */
     private Long id;
     /**
      * 车主,所属公司
      */
     private String carOwner;
     /**
      * 车牌号
      */
     private String carNumber;
     /**
      * 车型:1 特三,2丰田,3 希尔,4五菱,5春星,6货,7长城货,8长城,9 红宇,10 鸿星达,11 哈弗,12 霸道,13 雷克萨斯,14 雪佛兰,15 奔驰,16 江特,17 威尔法,18 奥迪,19 比亚迪,20 酷路泽,21 普拉多
      */
     private Integer carType;
     /**
      * 车辆品牌 （暂时存储车辆 车型的中文名称）
      */
     private String brand;
     /**
      * 车辆型号（暂时存储车辆 车型的中文名称）
      */
     private String model;
     /**
      * 油品类型 1 是 0#柴油； 2是  89#汽油；3 是92#汽油；4 是95#汽油；5 是 98#汽油
      */
     private Integer oilType;
     /**
      * 置购日期
      */
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime purchasingDate;
     /**
      * 载重 单位: 吨
      */
     private Integer carLoad;
     /**
      * 车辆备注
      */
     private String remarks;
     /**
      * 车辆使用状态 ：1 可用，2 外出中
      */
     private Integer activeState;
     /**
      * 车辆是否受车队管理： 0 不受车队管理车辆，1 受车队管理
      */
     private Integer isManage;
     /**
      * 车身状态： 0 报废车辆，1 正常使用，2 维修中 ，3 其他
      */
     private Integer state;
     /**
      * 处理人
      */
     private String updateMan;
     /**
      * 修改时间
      */
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime updateTime;
     /**
      * 创建时间
      */
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime createTime;

     //发车地址
     private String address;
}