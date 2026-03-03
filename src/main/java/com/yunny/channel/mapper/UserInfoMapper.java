package com.yunny.channel.mapper;

import com.yunny.channel.common.entity.UserInfoDO;
import com.yunny.channel.common.dto.UserInfoDTO;
import com.yunny.channel.common.query.UserInfoQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by fe
 */
@Mapper
public interface UserInfoMapper {

    /**
	* 查询总条数
	* @param userInfoQuery
	* @return
	*/
	Long countByQuery(UserInfoQuery userInfoQuery);

	/**
	 * 分页查询
	 * @param userInfoQuery
	 * @return
	 */
	List<UserInfoDO> listByQuery(UserInfoQuery userInfoQuery);

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	UserInfoDO getById(Long id);

	/**
	 * 插入
	 * @param userInfoDo
	 * @return
	 */
	int insertSelective(UserInfoDO userInfoDo);

	/**
	 * 更新
	 * @param userInfoDo
	 * @return
	 */
	int updateSelective(UserInfoDO userInfoDo);

	/**
	 *  <!-- 批量新增表user_info信息 -->
	 * @param list
	 * @return
	 */
	int batchInsertSelective(@Param("list")List<UserInfoDO> list);

}