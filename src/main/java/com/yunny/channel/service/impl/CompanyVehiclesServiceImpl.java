package com.yunny.channel.service.impl;

import com.yunny.channel.common.entity.CompanyVehiclesDO;
import com.yunny.channel.common.entity.VehiclesOutwardCardDO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.query.VehiclesOutwardCardQuery;
import com.yunny.channel.mapper.CompanyVehiclesMapper;
import com.yunny.channel.common.dto.CompanyVehiclesDTO;
import com.yunny.channel.common.vo.CompanyVehiclesVO;
import com.yunny.channel.common.query.CompanyVehiclesQuery;

import com.yunny.channel.mapper.VehiclesOutwardCardMapper;
import com.yunny.channel.service.CompanyVehiclesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;


/**
 * Created by Fe
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class CompanyVehiclesServiceImpl implements CompanyVehiclesService {

    @Resource
    private CompanyVehiclesMapper companyVehiclesMapper;

    @Resource
    private VehiclesOutwardCardMapper vehiclesOutwardCardMapper;


    /**
     * 分页查询
     *
     * @return
     */
    @Override
    public CommonPager<CompanyVehiclesVO> listByPage(CompanyVehiclesQuery companyVehiclesQuery) {
        PageParameter pageParameter = companyVehiclesQuery.getPageParameter();
        return new CommonPager<CompanyVehiclesVO>(
                new PageParameter(pageParameter.getCurrentPage(), pageParameter.getPageSize(),
                        companyVehiclesMapper.countByQuery(companyVehiclesQuery)),
                companyVehiclesMapper.listByQuery(companyVehiclesQuery).stream()
                        .map(item -> {
                            CompanyVehiclesVO companyVehiclesVo = new CompanyVehiclesVO();
                            BeanUtils.copyProperties(item, companyVehiclesVo);
                            return companyVehiclesVo;
                        }).collect(Collectors.toList()));
    }

    @Override
    public List<CompanyVehiclesVO> listAllByQuery() {
        return companyVehiclesMapper.listAllByQuery(CompanyVehiclesQuery.builder().isManage(1)
                .activeState(1).build()).stream()
                .map(item -> {
                    CompanyVehiclesVO companyVehiclesVo = new CompanyVehiclesVO();
                    BeanUtils.copyProperties(item, companyVehiclesVo);
                    return companyVehiclesVo;
                }).collect(Collectors.toList());
    }

    /**
     * 通过id获取
     *
     * @return
     */
    @Override
    public CompanyVehiclesVO getById(Long id) {
        CompanyVehiclesDO companyVehiclesDo = companyVehiclesMapper.getById(id);
        if (null == companyVehiclesDo) {
            return null;
        }
        CompanyVehiclesVO companyVehiclesVo = new CompanyVehiclesVO();
        BeanUtils.copyProperties(companyVehiclesDo, companyVehiclesVo);
        return companyVehiclesVo;
    }

    /**
     * 新增
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int insertSelective(CompanyVehiclesDTO companyVehiclesDto) {
        CompanyVehiclesDO companyVehiclesDo = new CompanyVehiclesDO();
        BeanUtils.copyProperties(companyVehiclesDto, companyVehiclesDo);
        return companyVehiclesMapper.insertSelective(companyVehiclesDo);
    }

    /**
     * 修改
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int updateSelective(CompanyVehiclesDTO companyVehiclesDto) {
        CompanyVehiclesDO companyVehiclesDo = new CompanyVehiclesDO();
        BeanUtils.copyProperties(companyVehiclesDto, companyVehiclesDo);
        return companyVehiclesMapper.updateSelective(companyVehiclesDo);
    }

    @Override
    public int updateSelectiveByCarNumber(CompanyVehiclesDTO companyVehiclesDto) {
        CompanyVehiclesDO companyVehiclesDo = new CompanyVehiclesDO();
        BeanUtils.copyProperties(companyVehiclesDto, companyVehiclesDo);
        companyVehiclesDo.setUpdateTime(LocalDateTime.now());
        return companyVehiclesMapper.updateSelectiveByCarNumber(companyVehiclesDo);
    }

    @Override
    public Long countCarNumberByQuery(CompanyVehiclesQuery query) {
        return companyVehiclesMapper.countCarNumberByQuery(query);
    }

    @Override
    public CompanyVehiclesVO getByCarNumber(String carNumber) {
        CompanyVehiclesDO companyVehiclesDo = companyVehiclesMapper.getByCarNumber(carNumber);
        if (null == companyVehiclesDo) {
            return null;
        }
        CompanyVehiclesVO companyVehiclesVo = new CompanyVehiclesVO();
        BeanUtils.copyProperties(companyVehiclesDo, companyVehiclesVo);
        return companyVehiclesVo;
    }


    @Override
    public int returnCompanyVehicles(CompanyVehiclesDTO companyVehiclesDto){
        //   //找到状态3的卡，变成 0 已使用
        List<VehiclesOutwardCardDO>  vehiclesOutwardCardDoList = vehiclesOutwardCardMapper.listByQuery(VehiclesOutwardCardQuery.builder()
                .carNumber(companyVehiclesDto.getCarNumber()).state(3).build());
        for (VehiclesOutwardCardDO vcdo : vehiclesOutwardCardDoList) {

            vehiclesOutwardCardMapper.updateSelectiveByCardNo(vcdo.builder().state(0)
                    .carNumber(vcdo.getCarNumber()).build());

        }

        companyVehiclesDto.setActiveState(1);//车辆使用状态 车辆可用
        CompanyVehiclesDO companyVehiclesDo = new CompanyVehiclesDO();
        BeanUtils.copyProperties(companyVehiclesDto, companyVehiclesDo);
        return companyVehiclesMapper.updateSelectiveByCarNumber(companyVehiclesDo);
    }

    public  Long countByQuery(CompanyVehiclesQuery companyVehiclesQuery){
        return  companyVehiclesMapper.countByQuery(companyVehiclesQuery);
    }
}
