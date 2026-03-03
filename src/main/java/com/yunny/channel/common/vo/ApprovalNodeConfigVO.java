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
 * 审批节点配置表
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalNodeConfigVO {
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