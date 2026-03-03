package com.yunny.channel.service;

import com.yunny.channel.common.dto.DictionaryDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.vo.DictionaryVO;
import com.yunny.channel.common.query.DictionaryQuery;

import java.util.List;

/**
 * Created by Fe
 */
public interface DictionaryService{

    CommonPager<DictionaryVO> listByPage(DictionaryQuery query);


    List<DictionaryVO> listByQuery(DictionaryQuery query);



    /**
    * 主键查询
    * @param id
    * @return
     */
    DictionaryVO getById(Long id);

    /**
     * 插入
     * @return
     */
    int insertSelective(DictionaryDTO dictionaryDto);

    /**
     * 修改
     * @return
     */
    int updateSelective(DictionaryDTO dictionaryDto);


}
