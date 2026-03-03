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
public class MaterialPlanDetailDTO {
     /**
      * 主键ID
      */
     private Long id;
     /**
      * 关联物资计划主表的plan_no
      */
     private String planNo;
     /**
      * 材料名称（如“铅笔”）
      */
     private String materialName;
     /**
      * 规格（如“HB”）
      */
     private String specification;
     /**
      * 数量（如10）
      */
     private Integer quantity;
     /**
      * 单位（如“支”“个”）
      */
     private String unit;
     /**
      * 单价（如1.50）
      */
     private BigDecimal unitPrice; // 可选，不填则为null
     /**
      * 明细金额（quantity*unit_price）
      */
     private BigDecimal amount;
     /**
      * 备注
      */
     private String remark;
     /**
      * 创建时间
      */
     private LocalDateTime createTime;
     /**
      * 更新时间
      */
     private LocalDateTime updateTime;
}