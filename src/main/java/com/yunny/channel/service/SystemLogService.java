package com.yunny.channel.service;

import com.yunny.channel.common.dto.SystemLogDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.vo.SystemLogVO;
import com.yunny.channel.common.query.SystemLogQuery;


/**
 * Created by Fe
 */
public interface SystemLogService{

    CommonPager<SystemLogVO> listByPage(SystemLogQuery query);

    /**
    * 主键查询
    * @param id
    * @return
     */
    SystemLogVO getById(Long id);

    /**
     * 插入
     * @return
     */
    int insertSelective(SystemLogDTO systemLogDto);

    /**
     * 修改
     * @return
     */
    int updateSelective(SystemLogDTO systemLogDto);


}
