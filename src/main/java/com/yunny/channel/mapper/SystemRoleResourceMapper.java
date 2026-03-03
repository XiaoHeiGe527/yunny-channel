package com.yunny.channel.mapper;

import com.yunny.channel.common.entity.SystemRoleResourceDO;
import com.yunny.channel.common.dto.SystemRoleResourceDTO;
import com.yunny.channel.common.query.SystemRoleResourceQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by fe
 */
public interface SystemRoleResourceMapper {

    /**
	* 查询总条数
	* @param systemRoleResourceQuery
	* @return
	*/
	Long countByQuery(SystemRoleResourceQuery systemRoleResourceQuery);

	/**
	 * 分页查询
	 * @param systemRoleResourceQuery
	 * @return
	 */
	List<SystemRoleResourceDO> listByQuery(SystemRoleResourceQuery systemRoleResourceQuery);

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	SystemRoleResourceDO getById(Long id);

	/**
	 * 插入
	 * @param systemRoleResourceDo
	 * @return
	 */
	int insertSelective(SystemRoleResourceDO systemRoleResourceDo);

	/**
	 * 更新
	 * @param systemRoleResourceDo
	 * @return
	 */
	int updateSelective(SystemRoleResourceDO systemRoleResourceDo);


	/**
	 * 根据角色ID删除
	 * @param roleId
	 * @return
	 */
	int deleteByRoleId(Integer roleId);


	int assignPermissions(SystemRoleResourceQuery systemRoleResourceQuery);


}