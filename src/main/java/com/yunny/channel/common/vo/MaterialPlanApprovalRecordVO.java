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
 * 计划审批记录表
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialPlanApprovalRecordVO {
     /**
      * 记录ID（主键）
      */
     private Long recordId;
     /**
      * 关联计划单号（material_plan_main.plan_no）
      */
     private String planNo;
     /**
      * 关联节点ID（approval_node_config.node_id）
      */
     private Integer nodeId;
     /**
      * 节点审批人（关联system_user.user_no）
      */
     private String approverNo;
     /**
      * 审批结果：0-待处理 1-已通过 2-已驳回 3-已跳过
      */
     private Integer approvalResult;
     /**
      * 是否跳过：0-否 1-是（申请人是该节点审批人）
      */
     private Integer skipFlag;
     /**
      * 跳过原因（如“申请人刘友金是该节点审批人，自动跳过”）
      */
     private String skipReason;
     /**
      * 审批意见（如“同意采购50顶安全帽”）
      */
     private String approvalOpinion;
     /**
      * 审批时间（待处理/已跳过为NULL）
      */
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime approvalTime;
     /**
      * 记录创建时间
      */
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime createTime;
}