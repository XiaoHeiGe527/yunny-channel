package com.yunny.channel.mapper;

import com.yunny.channel.common.entity.ChemicalFileImagesDO;
import com.yunny.channel.common.dto.ChemicalFileImagesDTO;
import com.yunny.channel.common.query.ChemicalFileImagesQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by fe
 */
public interface ChemicalFileImagesMapper {

    /**
	* 查询总条数
	* @param chemicalFileImagesQuery
	* @return
	*/
	Long countByQuery(ChemicalFileImagesQuery chemicalFileImagesQuery);

	/**
	 * 分页查询
	 * @param chemicalFileImagesQuery
	 * @return
	 */
	List<ChemicalFileImagesDO> listByQuery(ChemicalFileImagesQuery chemicalFileImagesQuery);

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	ChemicalFileImagesDO getById(Long id);

	/**
	 * 插入
	 * @param chemicalFileImagesDo
	 * @return
	 */
	int insertSelective(ChemicalFileImagesDO chemicalFileImagesDo);

	/**
	 * 更新
	 * @param chemicalFileImagesDo
	 * @return
	 */
	int updateSelective(ChemicalFileImagesDO chemicalFileImagesDo);

}