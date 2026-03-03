package com.yunny.channel.mapper;

import com.yunny.channel.common.entity.FilePositionDO;
import com.yunny.channel.common.dto.FilePositionDTO;
import com.yunny.channel.common.query.FilePositionQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by fe
 */
public interface FilePositionMapper {

    /**
	* 查询总条数
	* @param filePositionQuery
	* @return
	*/
	Long countByQuery(FilePositionQuery filePositionQuery);

	/**
	 * 分页查询
	 * @param filePositionQuery
	 * @return
	 */
	List<FilePositionDO> listByQuery(FilePositionQuery filePositionQuery);

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	FilePositionDO getById(Long id);

	/**
	 * 插入
	 * @param filePositionDo
	 * @return
	 */
	int insertSelective(FilePositionDO filePositionDo);

	/**
	 * 更新
	 * @param filePositionDo
	 * @return
	 */
	int updateSelective(FilePositionDO filePositionDo);

}