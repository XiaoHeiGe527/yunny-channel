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
public class FilePositionDO extends BaseDO{
     /**
      * 
      */
     private Long id;
     /**
      * 位置编码
      */
     private String positionCode;
     /**
      * 档案类型 1 技术文件；2 人事文件，3行政文件，4其他文件
      */
     private Integer fileType;
     /**
      * 区域
      */
     private String region;
     /**
      * 柜子编号
      */
     private String cabinetNum;
     /**
      * 柜子层级
      */
     private Integer cabinetLevel;
     /**
      * 备注
      */
     private String remarks;
     /**
      * 处理人
      */
     private String updateMan;
}