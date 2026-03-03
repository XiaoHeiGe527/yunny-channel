package com.yunny.channel.common.entity;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by fe
 * 物资计划明细表
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialPlanDetailDO extends BaseDO{
     /**
      * 主键ID
      */
     private Long id;
     /**
      * 关联主表plan_no
      */
     private String planNo;
     /**
      * 材料名称（如“安全帽”）
      */
     private String materialName;
     /**
      * 规格（如“XL号”）
      */
     private String specification;
     /**
      * 数量（如50）
      */
     private Integer quantity;
     /**
      * 单位（如“顶”“个”）
      */
     private String unit;
     /**
      * 单价（如25.00）
      */
     private BigDecimal unitPrice;
     /**
      * 明细金额（quantity*unit_price）
      */
     private BigDecimal amount;

     /**
      * 备注
      */
     private String remark;
}