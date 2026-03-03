package com.yunny.channel.mapper;

import com.yunny.channel.common.entity.SystemUserDepartmentDO;
import com.yunny.channel.common.dto.SystemUserDepartmentDTO;
import com.yunny.channel.common.query.SystemUserDepartmentQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by fe
 */
public interface SystemUserDepartmentMapper {

    /**
	* 查询总条数
	* @param systemUserDepartmentQuery
	* @return
	*/
	Long countByQuery(SystemUserDepartmentQuery systemUserDepartmentQuery);

	/**
	 * 分页查询
	 * @param systemUserDepartmentQuery
	 * @return
	 */
	List<SystemUserDepartmentDO> listByQuery(SystemUserDepartmentQuery systemUserDepartmentQuery);

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	SystemUserDepartmentDO getById(Long id);

	/**
	 * 插入
	 * @param systemUserDepartmentDo
	 * @return
	 */
	int insertSelective(SystemUserDepartmentDO systemUserDepartmentDo);

	/**
	 * 更新
	 * @param systemUserDepartmentDo
	 * @return
	 */
	int updateSelective(SystemUserDepartmentDO systemUserDepartmentDo);



	List<SystemUserDepartmentDO> listByUserDepartment(SystemUserDepartmentQuery systemUserDepartmentQuery);


}