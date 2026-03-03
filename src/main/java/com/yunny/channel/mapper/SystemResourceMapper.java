package com.yunny.channel.mapper;

import com.yunny.channel.common.entity.SystemResourceDO;
import com.yunny.channel.common.dto.SystemResourceDTO;
import com.yunny.channel.common.query.SystemResourceQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by fe
 */
public interface SystemResourceMapper {

    /**
	* 查询总条数
	* @param systemResourceQuery
	* @return
	*/
	Long countByQuery(SystemResourceQuery systemResourceQuery);

	/**
	 * 分页查询
	 * @param systemResourceQuery
	 * @return
	 */
	List<SystemResourceDO> listByQuery(SystemResourceQuery systemResourceQuery);

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	SystemResourceDO getById(Integer id);

	/**
	 * 插入
	 * @param systemResourceDo
	 * @return
	 */
	int insertSelective(SystemResourceDO systemResourceDo);

	/**
	 * 更新
	 * @param systemResourceDo
	 * @return
	 */
	int updateSelective(SystemResourceDO systemResourceDo);


	List<String> selectUserResourceList(String userNo);

	int  resourceDQ();


	List<SystemResourceDO> listByRoleId(Integer roleId);

	/**
	 * 加载用户2级导航
	 * @param userNo
	 * @return
	 */
	List<SystemResourceDO> selectUserValid2LevelResourcesByUserNo(String userNo);
}