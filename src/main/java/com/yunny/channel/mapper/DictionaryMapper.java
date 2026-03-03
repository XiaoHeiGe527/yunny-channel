package com.yunny.channel.mapper;

import com.yunny.channel.common.entity.DictionaryDO;
import com.yunny.channel.common.dto.DictionaryDTO;
import com.yunny.channel.common.query.DictionaryQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by fe
 */
public interface DictionaryMapper {

    /**
	* 查询总条数
	* @param dictionaryQuery
	* @return
	*/
	Long countByQuery(DictionaryQuery dictionaryQuery);

	/**
	 * 分页查询
	 * @param dictionaryQuery
	 * @return
	 */
	List<DictionaryDO> listByQuery(DictionaryQuery dictionaryQuery);

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	DictionaryDO getById(Long id);

	/**
	 * 插入
	 * @param dictionaryDo
	 * @return
	 */
	int insertSelective(DictionaryDO dictionaryDo);

	/**
	 * 更新
	 * @param dictionaryDo
	 * @return
	 */
	int updateSelective(DictionaryDO dictionaryDo);

}