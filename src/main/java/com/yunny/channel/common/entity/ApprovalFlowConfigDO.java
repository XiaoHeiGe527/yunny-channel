package com.yunny.channel.common.entity;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by fe
 * 审批流程配置表
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalFlowConfigDO extends BaseDO{
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
}