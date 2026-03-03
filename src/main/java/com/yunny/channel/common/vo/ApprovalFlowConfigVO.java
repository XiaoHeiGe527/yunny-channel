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
 * 审批流程配置表
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalFlowConfigVO {
     /**
      * 流程ID
      */
     private Integer flowId;
     /**
      * 流程名称（如“劳动保护审批流程”）
      */
     private String flowName;
     /**
      * 关联计划类型（与主表plan_type一致）
      */
     private String planType;
     /**
      * 流程描述（如“劳动保护需5级串行审批”）
      */
     private String flowDesc;
     /**
      * 流程状态：0-无效 1-有效
      */
     private Integer flowStatus;
     /**
      * 创建时间
      */
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime createTime;
}