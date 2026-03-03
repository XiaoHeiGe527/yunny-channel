package com.yunny.channel.common.vo;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

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
 * 物资计划主表
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialPlanMainVO {
     /**
      * 主键ID
      */
     private Long id;
     /**
      * 计划单号（格式：PLAN+年月日+3位序号，如PLAN20251101001）
      */
     private String planNo;
     /**
      * 计划类型：办公用品/劳动保护/生产相关/财务相关（化工）/财务相关（民爆）/车队相关
      */
     private String planType;
     /**
      * 申请部门（如办公室）
      */
     private String applyDept;
     /**
      * 申请人（关联system_user.user_no）
      */
     private String applicantNo;
     /**
      * 计划总金额（明细金额合计）
      */
     private BigDecimal totalAmount;
     /**
      * 计划事由（如“购买安全帽”）
      */
     private String planReason;
     /**
      * 申请日期
      */
     private LocalDateTime planDate;
     /**
      * 备注
      */
     private String remark;
     /**
      * 计划状态：0-草稿 1-待审批 2-审批中 3-已通过 4-已驳回
      */
     private Integer planStatus;
     /**
      * 创建时间
      */
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime createTime;
     /**
      * 更新时间
      */
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime updateTime;

     private String startTime;     // 申请开始日期（yyyy-MM-dd）
     private String endTime;       // 申请结束日期（yyyy-MM-dd）


     // 明细列表
     private List<MaterialPlanDetailVO> detailList;
}