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
public class SystemLogDO extends BaseDO{
     /**
      * 
      */
     private Long id;
     /**
      * 操作类型 增加 1 修改 2 删除3 下载 4
      */
     private Integer operationType;
     /**
      * 相关接口路径
      */
     private String url;
     /**
      * 日志详情
      */
     private String content;
     /**
      * 用户号
      */
     private String userNo;
     /**
      * 操作人名称
      */
     private String operatorName;
     /**
      * 备注
      */
     private String remarks;
     /**
      * ip
      */
     private String ip;
}