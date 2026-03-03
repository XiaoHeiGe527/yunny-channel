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
public class MaterialPlanMainQuery {
     // 原有分页参数
     private Integer currentPage;
     private Integer pageSize;
     private PageParameter pageParameter;

     // 新增筛选条件（根据业务需求添加）
     private String planNo;        // 计划单号（模糊查询）
     private String planType;      // 计划类型（如"办公用品"）
     private String applyDept;     // 申请部门
     private Integer planStatus;   // 计划状态（1-待审批等）
     private String startTime;     // 申请开始日期（yyyy-MM-dd）
     private String endTime;       // 申请结束日期（yyyy-MM-dd）

}