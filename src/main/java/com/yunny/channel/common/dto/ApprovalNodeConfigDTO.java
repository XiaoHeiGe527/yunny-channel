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
public class ApprovalNodeConfigDTO {
     /**
      * 节点ID（主键）
      */
     private Integer nodeId;
     /**
      * 关联流程ID（approval_flow_config.flow_id）
      */
     private Integer flowId;
     /**
      * 节点顺序（1→2→3...，串行核心）
      */
     private Integer nodeOrder;
     /**
      * 节点名称（如“李占霞审批”）
      */
     private String nodeName;
     /**
      * 审批人（关联system_user.user_no，如user_zhanxia）
      */
     private String approverNo;
     /**
      * 节点状态：0-无效 1-有效
      */
     private Integer nodeStatus;
}