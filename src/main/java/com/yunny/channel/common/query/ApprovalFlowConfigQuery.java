package com.yunny.channel.common.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.yunny.channel.common.page.PageParameter;

/**
 * Created by Fe
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalFlowConfigQuery {

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

     private Integer currentPage;

     private Integer pageSize;

     private PageParameter pageParameter;

}
