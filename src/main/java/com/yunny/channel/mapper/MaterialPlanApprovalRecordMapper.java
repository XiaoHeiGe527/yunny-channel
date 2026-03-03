package com.yunny.channel.mapper;

import com.yunny.channel.common.entity.MaterialPlanApprovalRecordDO;
import com.yunny.channel.common.query.MaterialPlanApprovalRecordQuery;

import java.util.List;

public interface MaterialPlanApprovalRecordMapper {


    /**
     * 查询总条数
     * @param materialPlanDetailQuery
     * @return
     */
    Long countByQuery(MaterialPlanApprovalRecordQuery materialPlanDetailQuery);

    /**
     * 分页查询
     * @param materialPlanDetailQuery
     * @return
     */
    List<MaterialPlanApprovalRecordDO> listByQuery(MaterialPlanApprovalRecordQuery materialPlanDetailQuery);



    /**
     * 插入
     * @param materialPlanDetailDo
     * @return
     */
    int insertSelective(MaterialPlanApprovalRecordDO materialPlanDetailDo);

    /**
     * 更新
     * @param materialPlanDetailDo
     * @return
     */
    int updateSelective(MaterialPlanApprovalRecordDO materialPlanDetailDo);
}
