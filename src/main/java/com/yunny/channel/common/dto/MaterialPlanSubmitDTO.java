package com.yunny.channel.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName MaterialPlanSubmitDTO
 * @Description TODO
 * @Author sunfuwei521@qq.com
 * @Date 2025/11/1 14:07
 */
/**
 * 物资计划提交DTO（前端传参）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialPlanSubmitDTO {

    // 主表信息
    @NotBlank(message = "计划类型不能为空")
    private String planType; // 计划类型：如"OFFICE_SUPPLY"（办公用品）

    @NotBlank(message = "申请部门不能为空")
    private String applyDept; // 申请部门：如"技术部"

    @NotBlank(message = "计划事由不能为空")
    private String planReason; // 事由：如"日常办公"

    private LocalDateTime planDate; // 申请日期
    private String remark; // 备注：如"紧急"

    // 明细列表（铅笔2盒、胶棒5个、剪刀1个）
    @NotEmpty(message = "至少填写一条物资明细")
    private List<MaterialPlanDetailDTO> detailList;
}
