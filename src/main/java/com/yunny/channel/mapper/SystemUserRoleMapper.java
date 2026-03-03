package com.yunny.channel.mapper;

import com.yunny.channel.common.entity.SystemUserRoleDO;
import com.yunny.channel.common.dto.SystemUserRoleDTO;
import com.yunny.channel.common.query.SystemUserRoleQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by fe
 */
public interface SystemUserRoleMapper {

    /**
	* 查询总条数
	* @param systemUserRoleQuery
	* @return
	*/
	Long countByQuery(SystemUserRoleQuery systemUserRoleQuery);

	/**
	 * 分页查询
	 * @param systemUserRoleQuery
	 * @return
	 */
	List<SystemUserRoleDO> listByQuery(SystemUserRoleQuery systemUserRoleQuery);

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	SystemUserRoleDO getById(Integer id);


	int deleteByUserNo(String userNo);

	SystemUserRoleDO getByUserNo(String userNo);

	/**
	 * 插入
	 * @param systemUserRoleDo
	 * @return
	 */
	int insertSelective(SystemUserRoleDO systemUserRoleDo);

	/**
	 * 更新
	 * @param systemUserRoleDo
	 * @return
	 */
	int updateSelective(SystemUserRoleDO systemUserRoleDo);


	int deleteByRoleId(String userNo);


	Long countByRoleId(Integer roleId);

}