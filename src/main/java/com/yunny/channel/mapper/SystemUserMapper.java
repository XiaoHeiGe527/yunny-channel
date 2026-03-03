package com.yunny.channel.mapper;

import com.yunny.channel.common.entity.SystemUserDO;
import com.yunny.channel.common.dto.SystemUserDTO;
import com.yunny.channel.common.query.LoginUserQuery;
import com.yunny.channel.common.query.SystemUserQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by fe
 */
public interface SystemUserMapper {

    /**
	* 查询总条数
	* @param systemUserQuery
	* @return
	*/
	Long countByQuery(SystemUserQuery systemUserQuery);

	/**
	 * 分页查询
	 * @param systemUserQuery
	 * @return
	 */
	List<SystemUserDO> listByQuery(SystemUserQuery systemUserQuery);

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	SystemUserDO getById(Long id);

	List<SystemUserDO> getByUserNo(String userNo);



	/**
	 * 插入
	 * @param systemUserDo
	 * @return
	 */
	int insertSelective(SystemUserDO systemUserDo);

	/**
	 * 更新
	 * @param systemUserDo
	 * @return
	 */
	int updateSelective(SystemUserDO systemUserDo);


	int updateByUserNo(SystemUserDO systemUserDo);


	/**
	 *  查询X系统用户
	 * @param query
	 * @return
	 */
	List<SystemUserDO> querySystemUserDOList(LoginUserQuery query);


	/**
	 * 查询用户
	 *
	 * @param systemUserQuery
	 * @return
	 */
	List<SystemUserDO> selectByPrimaryKey(SystemUserQuery systemUserQuery);


	/**
	 * 通过角色ID查询绑定的用户
	 * @param roleId
	 * @return
	 */
	List<SystemUserDO> getUsersByRoleId(Integer roleId);

}