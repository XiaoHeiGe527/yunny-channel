package com.yunny.channel.mapper;

import com.yunny.channel.common.entity.EmployeeFileDO;
import com.yunny.channel.common.dto.EmployeeFileDTO;
import com.yunny.channel.common.query.EmployeeFileQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by fe
 */
public interface EmployeeFileMapper {

    /**
	* 查询总条数
	* @param employeeFileQuery
	* @return
	*/
	Long countByQuery(EmployeeFileQuery employeeFileQuery);


	/**
	 * 获取当前姓名记录个数
	 * @param employeeFileQuery
	 * @return
	 */
	Long getnameCount(EmployeeFileQuery employeeFileQuery);


	/**
	 * 分页查询
	 * @param employeeFileQuery
	 * @return
	 */
	List<EmployeeFileDO> listByQuery(EmployeeFileQuery employeeFileQuery);

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	EmployeeFileDO getById(Integer id);

	/**
	 * 插入
	 * @param employeeFileDo
	 * @return
	 */
	int insertSelective(EmployeeFileDO employeeFileDo);

	/**
	 * 更新
	 * @param employeeFileDo
	 * @return
	 */
	int updateSelective(EmployeeFileDO employeeFileDo);

	/**
	 * 根据 employeeCode 统计数量（支持排除指定 ID，用于修改时校验）
	 * @param query 包含 employeeCode 和 excludeId（可选，修改时传入当前记录 ID）
	 * @return 符合条件的记录数
	 */
	Long countByEmployeeCode(@Param("query") EmployeeFileQuery query);


}