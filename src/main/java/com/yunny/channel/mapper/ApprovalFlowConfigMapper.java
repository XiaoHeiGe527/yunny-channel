package com.yunny.channel.mapper;

import com.yunny.channel.common.entity.ApprovalFlowConfigDO;
import com.yunny.channel.common.query.ApprovalFlowConfigQuery;

import java.util.List;

public interface ApprovalFlowConfigMapper {


    /**
     * 查询总条数
     * @param materialPlanDetailQuery
     * @return
     */
    Long countByQuery(ApprovalFlowConfigQuery materialPlanDetailQuery);

    /**
     * 分页查询
     * @param materialPlanDetailQuery
     * @return
     */
    List<ApprovalFlowConfigDO> listByQuery(ApprovalFlowConfigQuery materialPlanDetailQuery);



    /**
     * 插入
     * @param materialPlanDetailDo
     * @return
     */
    int insertSelective(ApprovalFlowConfigDO materialPlanDetailDo);

    /**
     * 更新
     * @param materialPlanDetailDo
     * @return
     */
    int updateSelective(ApprovalFlowConfigDO materialPlanDetailDo);

}
