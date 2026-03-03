package com.yunny.channel.mapper;

import com.yunny.channel.common.entity.SystemLogDO;
import com.yunny.channel.common.dto.SystemLogDTO;
import com.yunny.channel.common.query.SystemLogQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by fe
 */
public interface SystemLogMapper {

    /**
	* 查询总条数
	* @param systemLogQuery
	* @return
	*/
	Long countByQuery(SystemLogQuery systemLogQuery);

	/**
	 * 分页查询
	 * @param systemLogQuery
	 * @return
	 */
	List<SystemLogDO> listByQuery(SystemLogQuery systemLogQuery);

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	SystemLogDO getById(Long id);

	/**
	 * 插入
	 * @param systemLogDo
	 * @return
	 */
	int insertSelective(SystemLogDO systemLogDo);

	/**
	 * 更新
	 * @param systemLogDo
	 * @return
	 */
	int updateSelective(SystemLogDO systemLogDo);

}