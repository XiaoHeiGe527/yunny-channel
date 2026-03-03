package com.yunny.channel.mapper;

import com.yunny.channel.common.entity.EmployeeImagesDO;
import com.yunny.channel.common.dto.EmployeeImagesDTO;
import com.yunny.channel.common.query.EmployeeImagesQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by fe
 */
public interface EmployeeImagesMapper {

    /**
	* 查询总条数
	* @param employeeImagesQuery
	* @return
	*/
	Long countByQuery(EmployeeImagesQuery employeeImagesQuery);

	/**
	 * 分页查询
	 * @param employeeImagesQuery
	 * @return
	 */
	List<EmployeeImagesDO> listByQuery(EmployeeImagesQuery employeeImagesQuery);

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	EmployeeImagesDO getById(Long id);

	/**
	 * 插入
	 * @param employeeImagesDo
	 * @return
	 */
	int insertSelective(EmployeeImagesDO employeeImagesDo);

	/**
	 * 更新
	 * @param employeeImagesDo
	 * @return
	 */
	int updateSelective(EmployeeImagesDO employeeImagesDo);

}