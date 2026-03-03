package com.yunny.channel.mapper;

import com.yunny.channel.common.entity.MaterialPlanMainDO;
import com.yunny.channel.common.dto.MaterialPlanMainDTO;
import com.yunny.channel.common.query.MaterialPlanMainQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by fe
 */
public interface MaterialPlanMainMapper {

    /**
	* 查询总条数
	* @param materialPlanMainQuery
	* @return
	*/
	Long countByQuery(MaterialPlanMainQuery materialPlanMainQuery);

	/**
	 * 分页查询
	 * @param materialPlanMainQuery
	 * @return
	 */
	List<MaterialPlanMainDO> listByQuery(MaterialPlanMainQuery materialPlanMainQuery);

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	MaterialPlanMainDO getById(Long id);

	/**
	 * 插入
	 * @param materialPlanMainDo
	 * @return
	 */
	int insertSelective(MaterialPlanMainDO materialPlanMainDo);

	/**
	 * 更新
	 * @param materialPlanMainDo
	 * @return
	 */
	int updateSelective(MaterialPlanMainDO materialPlanMainDo);

}