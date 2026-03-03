package com.yunny.channel.service;

import com.yunny.channel.common.dto.CompanyVehiclesDTO;
import com.yunny.channel.common.entity.CompanyVehiclesDO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.vo.CompanyVehiclesVO;
import com.yunny.channel.common.query.CompanyVehiclesQuery;

import java.util.List;

/**
 * Created by Fe
 */
public interface CompanyVehiclesService{

    CommonPager<CompanyVehiclesVO> listByPage(CompanyVehiclesQuery query);

    /**
     * 查询集合
     *      * @return
     */
    List<CompanyVehiclesVO> listAllByQuery();

    /**
    * 主键查询
    * @param id
    * @return
     */
    CompanyVehiclesVO getById(Long id);

    /**
     * 插入
     * @return
     */
    int insertSelective(CompanyVehiclesDTO companyVehiclesDto);

    /**
     * 修改
     * @return
     */
    int updateSelective(CompanyVehiclesDTO companyVehiclesDto);



    /**
     * 根据车牌号修改修改
     * @return
     */
    int updateSelectiveByCarNumber(CompanyVehiclesDTO companyVehiclesDto);


    /**
     * 根据车牌号查询数量
     * @param query
     * @return
     */
    Long countCarNumberByQuery(CompanyVehiclesQuery query);

    CompanyVehiclesVO getByCarNumber(String carNumber);

    /**
     * 车辆归还
     * @param companyVehiclesDto
     * @return
     */
    int returnCompanyVehicles(CompanyVehiclesDTO companyVehiclesDto);


    /**
     * 根据查询条件查询数量
     * @param companyVehiclesQuery
     * @return
     */
    Long countByQuery(CompanyVehiclesQuery companyVehiclesQuery);


}
