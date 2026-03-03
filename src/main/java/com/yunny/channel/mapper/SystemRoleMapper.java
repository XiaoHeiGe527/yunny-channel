package com.yunny.channel.mapper;

import com.yunny.channel.common.entity.SystemRoleDO;
import com.yunny.channel.common.dto.SystemRoleDTO;
import com.yunny.channel.common.query.SystemRoleQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by fe
 */
public interface SystemRoleMapper {

    /**
	* 查询总条数
	* @param systemRoleQuery
	* @return
	*/
	Long countByQuery(SystemRoleQuery systemRoleQuery);

	/**
	 * 分页查询
	 * @param systemRoleQuery
	 * @return
	 */
	List<SystemRoleDO> listByQuery(SystemRoleQuery systemRoleQuery);

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	SystemRoleDO getById(Integer id);

	/**
	 * 插入
	 * @param systemRoleDo
	 * @return
	 */
	int insertSelective(SystemRoleDO systemRoleDo);

	/**
	 * 更新
	 * @param systemRoleDo
	 * @return
	 */
	int updateSelective(SystemRoleDO systemRoleDo);


	int deleteById(Integer id);

}