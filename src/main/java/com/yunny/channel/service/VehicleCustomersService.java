package com.yunny.channel.service;

import com.yunny.channel.common.dto.VehicleCustomersDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.vo.VehicleCustomersVO;
import com.yunny.channel.common.query.VehicleCustomersQuery;

import java.util.List;
import java.util.Set;

/**
 * Created by Fe
 */
public interface VehicleCustomersService{

    CommonPager<VehicleCustomersVO> listByPage(VehicleCustomersQuery query);

    /**
    * 主键查询
    * @param id
    * @return
     */
    VehicleCustomersVO getById(Integer id);

    /**
     * name查询
     * @param name
     * @return
     */
    VehicleCustomersVO getByName(String name);


    /**
     * 插入
     * @return
     */
    int insertSelective(VehicleCustomersDTO vehicleCustomersDto);

    /**
     * 修改
     * @return
     */
    int updateSelective(VehicleCustomersDTO vehicleCustomersDto);


    /**
     * 查询所有 客户信息
     * @param vehicleCustomersQuery
     * @return
     */
    List<VehicleCustomersVO> listAll(VehicleCustomersQuery vehicleCustomersQuery);


    /**
     * 模糊匹配车辆客户名称，并将客户名称存储到 Redis 中
     *
     * @param name 用于模糊匹配的名称
     * @return 包含匹配到的车辆客户名称的集合
     */
    Set<String> fuzzyMatchingVehicleCustomers(String name);


    Long countByQuery(VehicleCustomersQuery vehicleCustomersQuery);
}
