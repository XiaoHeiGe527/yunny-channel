package com.yunny.channel.service;

import com.yunny.channel.common.dto.EmployeeImagesDTO;
import com.yunny.channel.common.vo.EmployeeImagesVO;
import com.yunny.channel.common.query.EmployeeImagesQuery;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;

import java.util.List;

/**
 * Created by Fe
 */
public interface EmployeeImagesService{

    CommonPager<EmployeeImagesVO> listByPage(EmployeeImagesQuery query);

    /**
    * 主键查询
    * @param id
    * @return
     */
    EmployeeImagesVO getById(Long id);

    /**
     * 插入
     * @return
     */
    int insertSelective(EmployeeImagesDTO employeeImagesDto);

    /**
     * 修改
     * @return
     */
    int updateSelective(EmployeeImagesDTO employeeImagesDto);



    /**
     * 通过文件ID获取图片信息
     * @param query
     * @return
     */
     List<EmployeeImagesVO> listByEmployeeCode(EmployeeImagesQuery query);
}
