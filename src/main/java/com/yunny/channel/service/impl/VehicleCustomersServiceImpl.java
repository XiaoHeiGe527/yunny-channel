package com.yunny.channel.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunny.channel.common.constant.RedisKeyNameConstants;
import com.yunny.channel.common.entity.VehicleCustomersDO;
import com.yunny.channel.common.dto.VehicleCustomersDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.tools.redis.RedisService;
import com.yunny.channel.common.vo.VehicleCustomersVO;
import com.yunny.channel.common.query.VehicleCustomersQuery;
import com.yunny.channel.mapper.VehicleCustomersMapper;
import com.yunny.channel.service.VehicleCustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Created by Fe
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class VehicleCustomersServiceImpl implements VehicleCustomersService {

    @Resource
    private VehicleCustomersMapper vehicleCustomersMapper;

    // 用于 JSON 序列化和反序列化的对象
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    RedisService redisService;


    /**
     * 分页查询
     *
     * @return
     */
    @Override
    public CommonPager<VehicleCustomersVO> listByPage(VehicleCustomersQuery vehicleCustomersQuery) {
        PageParameter pageParameter = vehicleCustomersQuery.getPageParameter();
        return new CommonPager<VehicleCustomersVO>(
                new PageParameter(pageParameter.getCurrentPage(), pageParameter.getPageSize(),
                        vehicleCustomersMapper.countByQuery(vehicleCustomersQuery)),
                vehicleCustomersMapper.listByQuery(vehicleCustomersQuery).stream()
                        .map(item -> {
                            VehicleCustomersVO vehicleCustomersVo = new VehicleCustomersVO();
                            BeanUtils.copyProperties(item, vehicleCustomersVo);
                            return vehicleCustomersVo;
                        }).collect(Collectors.toList()));
    }

    /**
     * 通过id获取
     *
     * @return
     */
    @Override
    public VehicleCustomersVO getById(Integer id) {
        VehicleCustomersDO vehicleCustomersDo = vehicleCustomersMapper.getById(id);
        if (null == vehicleCustomersDo) {
            return null;
        }
        VehicleCustomersVO vehicleCustomersVo = new VehicleCustomersVO();
        BeanUtils.copyProperties(vehicleCustomersDo, vehicleCustomersVo);
        return vehicleCustomersVo;
    }

    @Override
    public VehicleCustomersVO getByName(String name) {
        VehicleCustomersDO vehicleCustomersDo = vehicleCustomersMapper.getByName(name);
        if (null == vehicleCustomersDo) {
            return null;
        }
        VehicleCustomersVO vehicleCustomersVo = new VehicleCustomersVO();
        BeanUtils.copyProperties(vehicleCustomersDo, vehicleCustomersVo);
        return vehicleCustomersVo;
    }

    /**
     * 新增
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int insertSelective(VehicleCustomersDTO vehicleCustomersDto) {
        VehicleCustomersDO vehicleCustomersDo = new VehicleCustomersDO();
        BeanUtils.copyProperties(vehicleCustomersDto, vehicleCustomersDo);
        return vehicleCustomersMapper.insertSelective(vehicleCustomersDo);
    }

    /**
     * 修改
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int updateSelective(VehicleCustomersDTO vehicleCustomersDto) {
        VehicleCustomersDO vehicleCustomersDo = new VehicleCustomersDO();
        BeanUtils.copyProperties(vehicleCustomersDto, vehicleCustomersDo);
        return vehicleCustomersMapper.updateSelective(vehicleCustomersDo);
    }

    @Override
    public List<VehicleCustomersVO> listAll(VehicleCustomersQuery vehicleCustomersQuery) {
        List<VehicleCustomersDO> vehicleCustomersDOList = vehicleCustomersMapper.listAll(vehicleCustomersQuery);
        return vehicleCustomersDOList.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    private VehicleCustomersVO convertToVO(VehicleCustomersDO vehicleCustomersDO) {
        return VehicleCustomersVO.builder()
                .id(vehicleCustomersDO.getId())
                .name(vehicleCustomersDO.getName())
                .address(vehicleCustomersDO.getAddress())
                .specs(vehicleCustomersDO.getSpecs())
                .remark(vehicleCustomersDO.getRemark())
                .phoneNumber(vehicleCustomersDO.getPhoneNumber())
                .serialNumber(vehicleCustomersDO.getSerialNumber())
                .updateMan(vehicleCustomersDO.getUpdateMan())
                .createTime(vehicleCustomersDO.getCreateTime())
                .updateTime(vehicleCustomersDO.getUpdateTime())
                .build();
    }


    /**
     * 模糊匹配车辆客户名称，并将客户名称存储到 Redis 中
     *
     * @param name 用于模糊匹配的名称
     * @return 包含匹配到的车辆客户名称的集合
     */
    @Override
    public Set<String> fuzzyMatchingVehicleCustomers(String name) {
        // 检查 Redis 中是否存在车辆客户名称集合的标识
        BaseResult<Map<String, Boolean>> mapBaseResult = redisService.hashExist(RedisKeyNameConstants.VEHICLE_CUSTOMERS_LIST);

        Map<String, Boolean> existMap = mapBaseResult.getData();
        Set<String> vehicleCustomersNameSet = null;

        // 如果 Redis 中不存在车辆客户名称集合的标识
        if (!existMap.get(RedisKeyNameConstants.EXIST)) {
            List<VehicleCustomersVO> vehicleCustomersVOList = this.listAll(VehicleCustomersQuery.builder().build());
            // 使用 LinkedHashSet 来保持顺序
            vehicleCustomersNameSet = vehicleCustomersVOList.stream()
                    .map(VehicleCustomersVO::getName)
                    .collect(Collectors.toCollection(java.util.LinkedHashSet::new));

            // 用于存储车辆客户名称集合的 JSON 字符串
            String vehicleCustomersNameSetJson = null;
            try {
                // 将车辆客户名称集合转换为 JSON 字符串
                vehicleCustomersNameSetJson = objectMapper.writeValueAsString(vehicleCustomersNameSet);
            } catch (JsonProcessingException e) {
                // 记录 JSON 处理异常信息
                log.error("将客户名称集合转换为 JSON 字符串时出错", e);
            }

            // 如果成功将集合转换为 JSON 字符串
            if (vehicleCustomersNameSetJson != null) {
                // 将 JSON 字符串存储到 Redis 中
                redisService.addToken(RedisKeyNameConstants.VEHICLE_CUSTOMERS_LIST, vehicleCustomersNameSetJson);
            }
        } else {
            // 从 Redis 中获取车辆客户名称集合的 JSON 字符串
            String vehicleCustomersNameSetJson = redisService.getStringKey(RedisKeyNameConstants.VEHICLE_CUSTOMERS_LIST);
            try {
                // 将 JSON 字符串转换回车辆客户名称集合，使用 LinkedHashSet 保持顺序
                vehicleCustomersNameSet = objectMapper.readValue(vehicleCustomersNameSetJson,
                        objectMapper.getTypeFactory().constructCollectionType(java.util.LinkedHashSet.class, String.class));
            } catch (IOException e) {
                // 记录 JSON 处理异常信息
                log.error("将 JSON 字符串转换为客户名称集合时出错", e);
            }
        }

        // 使用 Stream API 过滤出包含输入名称的客户名称，存储到新的集合中，同样使用 LinkedHashSet 保持顺序
        Set<String> matchNameSet = vehicleCustomersNameSet.stream()
                .filter(customerName -> customerName.contains(name))
                .collect(Collectors.toCollection(java.util.LinkedHashSet::new));

        // 返回包含匹配名称的集合
        return matchNameSet;
    }

    @Override
    public Long countByQuery(VehicleCustomersQuery vehicleCustomersQuery) {
        return vehicleCustomersMapper.countByQuery(vehicleCustomersQuery);
    }

}
