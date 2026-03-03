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
 * 物资计划明细表
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialPlanDetailVO {
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
      * 备注
      */
     private String remark;

     /**
      * 单价（如25.00）
      */
     private BigDecimal unitPrice;
     /**
      * 明细金额（quantity*unit_price）
      */
     private BigDecimal amount;
     /**
      * 创建时间
      */
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime createTime;
}