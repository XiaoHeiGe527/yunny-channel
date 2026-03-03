package com.yunny.channel.service.impl;

import com.yunny.channel.common.entity.MaterialPlanDetailDO;

import com.yunny.channel.common.dto.MaterialPlanDetailDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.vo.MaterialPlanDetailVO;
import com.yunny.channel.common.query.MaterialPlanDetailQuery;
import com.yunny.channel.mapper.MaterialPlanDetailMapper;
import com.yunny.channel.service.MaterialPlanDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import java.util.stream.Collectors;


/**
 * Created by Fe
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class MaterialPlanDetailServiceImpl implements MaterialPlanDetailService {

    @Resource
    private MaterialPlanDetailMapper materialPlanDetailMapper;

    /**
    * 分页查询
    * @return
    */
    @Override
    public CommonPager<MaterialPlanDetailVO> listByPage(MaterialPlanDetailQuery materialPlanDetailQuery) {
        PageParameter pageParameter = materialPlanDetailQuery.getPageParameter();
        return new CommonPager<MaterialPlanDetailVO>(
                new PageParameter(pageParameter.getCurrentPage(), pageParameter.getPageSize(),
                        materialPlanDetailMapper.countByQuery(materialPlanDetailQuery)),
                materialPlanDetailMapper.listByQuery(materialPlanDetailQuery).stream()
                        .map(item -> {
                            MaterialPlanDetailVO materialPlanDetailVo = new MaterialPlanDetailVO();
                            BeanUtils.copyProperties(item, materialPlanDetailVo);
                            return materialPlanDetailVo;
                        }).collect(Collectors.toList()));
    }

    /**
    * 通过id获取
    * @return
    */
    @Override
    public MaterialPlanDetailVO getById(Long id) {
    	MaterialPlanDetailDO materialPlanDetailDo = materialPlanDetailMapper.getById(id);
    	if(null == materialPlanDetailDo){
    		return null;
    	}
    	MaterialPlanDetailVO materialPlanDetailVo = new MaterialPlanDetailVO();
    	BeanUtils.copyProperties(materialPlanDetailDo, materialPlanDetailVo);
    	return materialPlanDetailVo;
    }

    /**
    * 新增
    * @return
    */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int insertSelective(MaterialPlanDetailDTO materialPlanDetailDto) {
        MaterialPlanDetailDO materialPlanDetailDo = new MaterialPlanDetailDO();
        BeanUtils.copyProperties(materialPlanDetailDto, materialPlanDetailDo);
        return materialPlanDetailMapper.insertSelective(materialPlanDetailDo);
    }

    /**
    * 修改
    * @return
    */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int updateSelective(MaterialPlanDetailDTO materialPlanDetailDto) {
        MaterialPlanDetailDO materialPlanDetailDo = new MaterialPlanDetailDO();
        BeanUtils.copyProperties(materialPlanDetailDto, materialPlanDetailDo);
        return materialPlanDetailMapper.updateSelective(materialPlanDetailDo);
    }

}
