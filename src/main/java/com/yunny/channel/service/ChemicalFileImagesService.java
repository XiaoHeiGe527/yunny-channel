package com.yunny.channel.service;

import com.yunny.channel.common.dto.ChemicalFileImagesDTO;
import com.yunny.channel.common.entity.ChemicalFileImagesDO;
import com.yunny.channel.common.vo.ChemicalFileImagesVO;
import com.yunny.channel.common.query.ChemicalFileImagesQuery;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;

import java.util.List;

/**
 * Created by Fe
 */
public interface ChemicalFileImagesService{

    CommonPager<ChemicalFileImagesVO> listByPage(ChemicalFileImagesQuery query);

    /**
    * 主键查询
    * @param id
    * @return
     */
    ChemicalFileImagesVO getById(Long id);

    /**
     * 插入
     * @return
     */
    int insertSelective(ChemicalFileImagesDTO chemicalFileImagesDto);

    /**
     * 修改
     * @return
     */
    int updateSelective(ChemicalFileImagesDTO chemicalFileImagesDto);


    /**
     * 通过文件ID获取图片信息
     * @param chemicalFileImagesQuery
     * @return
     */
    List<ChemicalFileImagesVO>  listByChemicalFileId(ChemicalFileImagesQuery chemicalFileImagesQuery);


}
