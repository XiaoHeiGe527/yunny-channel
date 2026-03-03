package com.yunny.channel.service;

import com.yunny.channel.common.dto.FilePositionDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.vo.FilePositionVO;
import com.yunny.channel.common.query.FilePositionQuery;

import java.util.List;
import java.util.Set;

/**
 * Created by Fe
 */
public interface FilePositionService{

    CommonPager<FilePositionVO> listByPage(FilePositionQuery query);


    /**
     * 查询位置集合
     * @param query
     * @return
     */
    List<FilePositionVO> listByQuery(FilePositionQuery query);

    /**
    * 主键查询
    * @param id
    * @return
     */
    FilePositionVO getById(Long id);

    /**
     * 插入
     * @return
     */
    int insertSelective(FilePositionDTO filePositionDto);

    /**
     * 修改
     * @return
     */
    int updateSelective(FilePositionDTO filePositionDto);


}
