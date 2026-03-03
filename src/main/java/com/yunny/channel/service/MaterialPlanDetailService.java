package com.yunny.channel.service;

import com.yunny.channel.common.dto.MaterialPlanDetailDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.vo.MaterialPlanDetailVO;
import com.yunny.channel.common.query.MaterialPlanDetailQuery;

/**
 * Created by Fe
 */
public interface MaterialPlanDetailService{

    CommonPager<MaterialPlanDetailVO> listByPage(MaterialPlanDetailQuery query);

    /**
    * 主键查询
    * @param id
    * @return
     */
    MaterialPlanDetailVO getById(Long id);

    /**
     * 插入
     * @return
     */
    int insertSelective(MaterialPlanDetailDTO materialPlanDetailDto);

    /**
     * 修改
     * @return
     */
    int updateSelective(MaterialPlanDetailDTO materialPlanDetailDto);


}
