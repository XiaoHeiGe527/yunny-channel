package com.yunny.channel.service;

import com.yunny.channel.common.dto.MaterialPlanMainDTO;
import com.yunny.channel.common.dto.MaterialPlanSubmitDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.MaterialPlanMainVO;
import com.yunny.channel.common.query.MaterialPlanMainQuery;


/**
 * Created by Fe
 */
public interface MaterialPlanMainService{



    /**
     * 提交物资计划（返回统一结果）
     * @param submitDTO 计划主表+明细参数
     * @param applicantNo 申请人编号
     * @return BaseResult 成功/失败结果
     */
    BaseResult submitPlan(MaterialPlanSubmitDTO submitDTO, String applicantNo);

    CommonPager<MaterialPlanMainVO> listByPage(MaterialPlanMainQuery query);

    /**
    * 主键查询
    * @param id
    * @return
     */
    MaterialPlanMainVO getById(Long id);

    /**
     * 插入
     * @return
     */
    int insertSelective(MaterialPlanMainDTO materialPlanMainDto);

    /**
     * 修改
     * @return
     */
    int updateSelective(MaterialPlanMainDTO materialPlanMainDto);


}
