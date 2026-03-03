package com.yunny.channel.mapper;

import com.yunny.channel.common.entity.ChemicalFileDO;
import com.yunny.channel.common.dto.ChemicalFileDTO;
import com.yunny.channel.common.query.ChemicalFileQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by fe
 */
public interface ChemicalFileMapper {

    /**
	* 查询总条数
	* @param chemicalFileQuery
	* @return
	*/
	Long countByQuery(ChemicalFileQuery chemicalFileQuery);

	/**
	 * 分页查询
	 * @param chemicalFileQuery
	 * @return
	 */
	List<ChemicalFileDO> listByQuery(ChemicalFileQuery chemicalFileQuery);

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	ChemicalFileDO getById(Long id);

	/**
	 * 插入
	 * @param chemicalFileDo
	 * @return
	 */
	int insertSelective(ChemicalFileDO chemicalFileDo);

	/**
	 * 更新
	 * @param chemicalFileDo
	 * @return
	 */
	int updateSelective(ChemicalFileDO chemicalFileDo);

}