package com.yunny.channel.mapper;

import com.yunny.channel.common.entity.MaterialPlanDetailDO;
import com.yunny.channel.common.dto.MaterialPlanDetailDTO;
import com.yunny.channel.common.query.MaterialPlanDetailQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by fe
 */
public interface MaterialPlanDetailMapper {

    /**
	* 查询总条数
	* @param materialPlanDetailQuery
	* @return
	*/
	Long countByQuery(MaterialPlanDetailQuery materialPlanDetailQuery);

	/**
	 * 分页查询
	 * @param materialPlanDetailQuery
	 * @return
	 */
	List<MaterialPlanDetailDO> listByQuery(MaterialPlanDetailQuery materialPlanDetailQuery);

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	MaterialPlanDetailDO getById(Long id);

	/**
	 * 插入
	 * @param materialPlanDetailDo
	 * @return
	 */
	int insertSelective(MaterialPlanDetailDO materialPlanDetailDo);

	/**
	 * 更新
	 * @param materialPlanDetailDo
	 * @return
	 */
	int updateSelective(MaterialPlanDetailDO materialPlanDetailDo);


	List<MaterialPlanDetailDO> listByPlanNos(@Param("planNos") List<String> planNos);

}