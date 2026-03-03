package com.yunny.channel.mapper;

import com.yunny.channel.common.entity.ApprovalFlowConfigDO;
import com.yunny.channel.common.entity.ApprovalNodeConfigDO;
import com.yunny.channel.common.query.ApprovalFlowConfigQuery;
import com.yunny.channel.common.query.ApprovalNodeConfigQuery;

import java.util.List;

public interface ApprovalNodeConfigMapper {

    /**
     * 查询总条数
     * @param materialPlanDetailQuery
     * @return
     */
    Long countByQuery(ApprovalNodeConfigQuery materialPlanDetailQuery);

    /**
     * 分页查询
     * @param materialPlanDetailQuery
     * @return
     */
    List<ApprovalNodeConfigDO> listByQuery(ApprovalNodeConfigQuery materialPlanDetailQuery);



    /**
     * 插入
     * @param materialPlanDetailDo
     * @return
     */
    int insertSelective(ApprovalNodeConfigDO materialPlanDetailDo);

    /**
     * 更新
     * @param materialPlanDetailDo
     * @return
     */
    int updateSelective(ApprovalNodeConfigDO materialPlanDetailDo);
}
