package com.yunny.channel.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.util.StringUtils;
import com.yunny.channel.common.code.MobileMessageCode;
import com.yunny.channel.common.constant.CommonConstant;
import com.yunny.channel.common.constant.RedisKeyNameConstants;
import com.yunny.channel.common.constant.ServiceConstants;
import com.yunny.channel.common.dto.CompanyVehiclesDTO;
import com.yunny.channel.common.entity.VehicleInsuranceDO;

import com.yunny.channel.common.dto.VehicleInsuranceDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.query.CompanyVehiclesQuery;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.tools.redis.RedisService;
import com.yunny.channel.common.util.NumberUtils;
import com.yunny.channel.common.vo.VehicleInsuranceVO;
import com.yunny.channel.common.query.VehicleInsuranceQuery;
import com.yunny.channel.mapper.VehicleInsuranceMapper;
import com.yunny.channel.service.CompanyVehiclesService;
import com.yunny.channel.service.VehicleInsuranceService;
import com.yunny.channel.util.MyExcelUtilHuTu;
import com.yunny.channel.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by Fe
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class VehicleInsuranceServiceImpl implements VehicleInsuranceService {

    @Resource
    private VehicleInsuranceMapper vehicleInsuranceMapper;

    @Resource
    private CompanyVehiclesService companyVehiclesService;


    @Autowired
    RedisService redisService;


    /**
     * 分页查询
     *
     * @return
     */
    @Override
    public CommonPager<VehicleInsuranceVO> listByPage(VehicleInsuranceQuery vehicleInsuranceQuery) {
        PageParameter pageParameter = vehicleInsuranceQuery.getPageParameter();
        return new CommonPager<VehicleInsuranceVO>(
                new PageParameter(pageParameter.getCurrentPage(), pageParameter.getPageSize(),
                        vehicleInsuranceMapper.countByQuery(vehicleInsuranceQuery)),
                vehicleInsuranceMapper.listByQuery(vehicleInsuranceQuery).stream()
                        .map(item -> {
                            VehicleInsuranceVO vehicleInsuranceVo = new VehicleInsuranceVO();
                            BeanUtils.copyProperties(item, vehicleInsuranceVo);
                            vehicleInsuranceVo.setCreateTime(item.getCreateTime());
                            return vehicleInsuranceVo;
                        }).collect(Collectors.toList()));
    }

    @Override
    public List<VehicleInsuranceVO> listByExpirationReminder(VehicleInsuranceQuery vehicleInsuranceQuery) {
        vehicleInsuranceQuery.setExpiryAlert(TimeUtil.timeFormatString(LocalDateTime.now(), TimeUtil.DATE_TIME_FORMAT));
//        log.info("承包人",vehicleInsuranceQuery.getInsured());
//        log.info("开始时间[{}]",vehicleInsuranceQuery.getUnderwritingDateStart());
//        log.info("结束时间[{}]",vehicleInsuranceQuery.getUnderwritingDateEnd());
        return vehicleInsuranceMapper.listByExpirationReminder(vehicleInsuranceQuery).stream()
                .map(doObj -> {
                    VehicleInsuranceVO voObj = new VehicleInsuranceVO();
                    voObj.setId(doObj.getId());
                    voObj.setBusinessTax(doObj.getBusinessTax());
                    voObj.setCarNumber(doObj.getCarNumber());
                    voObj.setCarTypeCn(doObj.getCarTypeCn());
                    voObj.setCompulsoryInsurance(doObj.getCompulsoryInsurance());
                    voObj.setCreateTime(doObj.getCreateTime());
                    voObj.setInsured(doObj.getInsured());
                    voObj.setNonMotorInsurance(doObj.getNonMotorInsurance());
                    voObj.setPolicyExpiryAlert(doObj.getPolicyExpiryAlert());
                    voObj.setTotal(doObj.getTotal());
                    voObj.setUnderwritingDate(doObj.getUnderwritingDate());
                    voObj.setUpdateTime(doObj.getUpdateTime());
                    voObj.setVehicleAndVesselTax(doObj.getVehicleAndVesselTax());
                    voObj.setCreateTime(doObj.getCreateTime());
                    voObj.setRemarks(doObj.getRemarks());
                    return voObj;
                }).collect(Collectors.toList());
    }

    @Override
    public Long expirationReminderCount(VehicleInsuranceQuery vehicleInsuranceQuery) {
        vehicleInsuranceQuery.setExpiryAlert(TimeUtil.timeFormatString(LocalDateTime.now(), TimeUtil.DATE_TIME_FORMAT));
//        log.info("承包人",vehicleInsuranceQuery.getInsured());
//        log.info("开始时间[{}]",vehicleInsuranceQuery.getUnderwritingDateStart());
//        log.info("结束时间[{}]",vehicleInsuranceQuery.getUnderwritingDateEnd());
        return vehicleInsuranceMapper.expirationReminderCount(vehicleInsuranceQuery);
    }

    @Transactional
    @Override
    public  Map<String,String> importVehicleInsuranceExcel(MultipartFile file) {
        if (!file.getOriginalFilename().endsWith(".xls") && !file.getOriginalFilename().endsWith(".xlsx")) {
            throw new RuntimeException("只支持Excel文件(.xls, .xlsx)");
        }

        // 验证文件是否为空
        if (file.isEmpty()) {
            throw new RuntimeException("上传的文件为空");
        }


      String   batchNo = this.getBatchNo();

        try {
            // 1. 使用MultipartFile的输入流直接读取Excel
            ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
            // 2. 设置表头别名
            reader.addHeaderAlias("承保日期", "underwritingDate");
            reader.addHeaderAlias("投保人", "insured");
            reader.addHeaderAlias("车牌号", "carNumber");
            reader.addHeaderAlias("车型", "carTypeCn");
            reader.addHeaderAlias("交强", "compulsoryInsurance");
            reader.addHeaderAlias("车船税", "vehicleAndVesselTax");
            reader.addHeaderAlias("商业", "businessTax");
            reader.addHeaderAlias("非车", "nonMotorInsurance");
            reader.addHeaderAlias("共计", "total");
            // 3. 读取数据
            List<VehicleInsuranceDO> list = reader.readAll(VehicleInsuranceDO.class);

            if (list.size() > 200) {
                throw new RuntimeException("不允许超过200条！");
            }

            // 4. 处理数据
            for (VehicleInsuranceDO vdo : list) {
                // 设置提醒日期
                vdo.setPolicyExpiryAlert(vdo.getUnderwritingDate().plusMonths(CommonConstant.REMINDER_MONTHS));
                // 检查并设置默认值
                if (StringUtils.isEmpty(vdo.getCompulsoryInsurance())) {
                    vdo.setCompulsoryInsurance("0");
                }
                if (StringUtils.isEmpty(vdo.getVehicleAndVesselTax())) {
                    vdo.setVehicleAndVesselTax("0");
                }
                if (StringUtils.isEmpty(vdo.getBusinessTax())) {
                    vdo.setBusinessTax("0");
                }
                if (StringUtils.isEmpty(vdo.getNonMotorInsurance())) {
                    vdo.setNonMotorInsurance("0");
                }
                String oldCarNumber = vdo.getCarNumber();
                // 处理车牌号：去除所有空格和横线
                String newCarNumber = oldCarNumber.replaceAll("[\\s-]+", "");
                vdo.setCarNumber(newCarNumber);
                Map<String, Integer> carTypeMap = this.getcarTypeMap();
                vdo.setCarType(carTypeMap.get(vdo.getCarTypeCn()));
                //检查是否存在这个车牌号的车辆，如果没有就入库
                Long carCount = companyVehiclesService.countCarNumberByQuery(CompanyVehiclesQuery.builder().carNumber(newCarNumber).build());
                if (carCount == 0) {
                    CompanyVehiclesDTO companyVehiclesDto = CompanyVehiclesDTO.builder().carNumber(vdo.getCarNumber())
                            .carType(vdo.getCarType()).createTime(LocalDateTime.now()).remarks(vdo.getCarTypeCn()).build();
                    // 设置处理后的规范车牌号
                    companyVehiclesDto.setCarNumber(newCarNumber);
                    companyVehiclesService.insertSelective(companyVehiclesDto);
                }

                Map<String, Integer> map = this.getcarTypeMap();
                vdo.setCarType(map.get(vdo.getCarTypeCn()));
                vdo.setBatchNo(batchNo);
            }
            int count = vehicleInsuranceMapper.batchInsert(list);

            Map<String,String> map =  new HashMap<>();
            map.put("batch",batchNo);
            map.put("count",String.valueOf(count));

            return map;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("处理Excel文件失败", e);
        }
    }

    @Override
    public BaseResult handleCard() {
        List<VehicleInsuranceVO> vehicleInsuranceVOList = this.listAll(new VehicleInsuranceQuery());
        //获取车型中文名与对应的 type数字
        // Map<String,Integer> map = this.getcarTypeMap();
        //遍历 承保车辆信息
        for (VehicleInsuranceVO vo : vehicleInsuranceVOList) {

//            String oldCarNumber = vo.getCarNumber();
//            // 处理车牌号：去除所有空格和横线
//            String newCarNumber = oldCarNumber.replaceAll("[\\s-]+", "");
//            // 设置处理后的规范车牌号
//            vo.setCarNumber(newCarNumber);
//            VehicleInsuranceDO vehicleInsuranceDo = VehicleInsuranceDO.builder().id(vo.getId())
//                    .carNumber(vo.getCarNumber())
//                    .build();
//            vehicleInsuranceMapper.updateSelective(vehicleInsuranceDo);

            //将承保车辆信息处理好入进公司车表中

            Long carCount = companyVehiclesService.countCarNumberByQuery(CompanyVehiclesQuery.builder().carNumber(vo.getCarNumber()).build());
            if (carCount == 0) {

                CompanyVehiclesDTO companyVehiclesDto = CompanyVehiclesDTO.builder().carNumber(vo.getCarNumber())
                        .carType(vo.getCarType()).createTime(LocalDateTime.now()).carOwner(vo.getInsured())
                        .brand(vo.getCarTypeCn()).model(vo.getCarTypeCn()).activeState(1).state(1)
                        .build();

                List<String> managerdCarNumbers = ServiceConstants.MANAGED_CAR_NUMBERS;
                Set<String> carNumberSet = new HashSet<>(managerdCarNumbers);
                boolean exists = carNumberSet.contains(vo.getCarNumber()); // true 表示存在，false 表示不存在
                if (exists) {

                    companyVehiclesDto.setIsManage(1);
                } else {
                    companyVehiclesDto.setIsManage(0);
                }
                companyVehiclesService.insertSelective(companyVehiclesDto);
            }

        }

        return BaseResult.success();
    }

    @Override
    public Long countByQuery(VehicleInsuranceQuery vehicleInsuranceQuery) {
        return vehicleInsuranceMapper.countByQuery(vehicleInsuranceQuery);
    }

    @Override
    public List<VehicleInsuranceVO> listAll(VehicleInsuranceQuery vehicleInsuranceQuery) {
        vehicleInsuranceQuery.setExpiryAlert(TimeUtil.timeFormatString(LocalDateTime.now(), TimeUtil.DATE_TIME_FORMAT));
//        log.info("承包人",vehicleInsuranceQuery.getInsured());
//        log.info("开始时间[{}]",vehicleInsuranceQuery.getUnderwritingDateStart());
//        log.info("结束时间[{}]",vehicleInsuranceQuery.getUnderwritingDateEnd());
        return vehicleInsuranceMapper.listAll(vehicleInsuranceQuery).stream()
                .map(doObj -> {
                    VehicleInsuranceVO voObj = new VehicleInsuranceVO();
                    voObj.setId(doObj.getId());
                    voObj.setCarType(doObj.getCarType());
                    voObj.setBusinessTax(doObj.getBusinessTax());
                    voObj.setCarNumber(doObj.getCarNumber());
                    voObj.setCarTypeCn(doObj.getCarTypeCn());
                    voObj.setCompulsoryInsurance(doObj.getCompulsoryInsurance());
                    voObj.setCreateTime(doObj.getCreateTime());
                    voObj.setInsured(doObj.getInsured());
                    voObj.setNonMotorInsurance(doObj.getNonMotorInsurance());
                    voObj.setPolicyExpiryAlert(doObj.getPolicyExpiryAlert());
                    voObj.setTotal(doObj.getTotal());
                    voObj.setUnderwritingDate(doObj.getUnderwritingDate());
                    voObj.setUpdateTime(doObj.getUpdateTime());
                    voObj.setVehicleAndVesselTax(doObj.getVehicleAndVesselTax());
                    voObj.setRemarks(doObj.getRemarks());
                    return voObj;
                }).collect(Collectors.toList());
    }

    /**
     * 通过id获取
     *
     * @return
     */
    @Override
    public VehicleInsuranceVO getById(Long id) {
        VehicleInsuranceDO vehicleInsuranceDo = vehicleInsuranceMapper.getById(id);
        if (null == vehicleInsuranceDo) {
            return null;
        }
        VehicleInsuranceVO vehicleInsuranceVo = new VehicleInsuranceVO();
        BeanUtils.copyProperties(vehicleInsuranceDo, vehicleInsuranceVo);
        return vehicleInsuranceVo;
    }

    /**
     * 新增
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int insertSelective(VehicleInsuranceDTO vehicleInsuranceDto, String userNo) {
       String batchNo = this.getBatchNo();
        return vehicleInsuranceMapper.insertSelective(this.insertHandleDO(vehicleInsuranceDto, userNo,batchNo));
    }

    /**
     * 修改
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int updateSelective(VehicleInsuranceDTO vehicleInsuranceDto) {
        VehicleInsuranceDO vehicleInsuranceDo = new VehicleInsuranceDO();
        BeanUtils.copyProperties(vehicleInsuranceDto, vehicleInsuranceDo);
        vehicleInsuranceDo.setId(vehicleInsuranceDto.getId().longValue());
        return vehicleInsuranceMapper.updateSelective(vehicleInsuranceDo);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Map<String,String> batchInsert(List<VehicleInsuranceDTO> dtoList, String userNo) {
        List<VehicleInsuranceDO> insuranceList = new ArrayList<>();
      String batch = this.getBatchNo();
        for (VehicleInsuranceDTO dto : dtoList) {
            String newCarNumber = dto.getCarNumber().replaceAll("[\\s-]+", "");


            //检查是否存在这个车牌号的车辆，如果没有就入库
            Long carCount = companyVehiclesService.countCarNumberByQuery(CompanyVehiclesQuery.builder().carNumber(newCarNumber).build());
            if (carCount == 0) {
                CompanyVehiclesDTO companyVehiclesDto = CompanyVehiclesDTO.builder()
                        .carNumber(dto.getCarNumber())
                        .carType(dto.getCarType()).createTime(LocalDateTime.now()).remarks(dto.getCarTypeCn()).build();
                companyVehiclesService.insertSelective(companyVehiclesDto);
            }

            insuranceList.add(this.insertHandleDO(dto, userNo,batch));

        }
        int count = vehicleInsuranceMapper.batchInsert(insuranceList);

        Map<String,String> map =  new HashMap<>();
        map.put("batch",batch);
        map.put("count",String.valueOf(count));
        return map;
    }

    @Override
    public int renewInsurance(VehicleInsuranceQuery vehicleInsuranceQuery, String userNo) {
        VehicleInsuranceVO vehicleInsuranceVO = this.getById(vehicleInsuranceQuery.getId());
        if (vehicleInsuranceVO != null) {
            VehicleInsuranceDO vehicleInsuranceDo = new VehicleInsuranceDO();
            vehicleInsuranceDo.setId(vehicleInsuranceVO.getId());
            vehicleInsuranceDo.setInsured(vehicleInsuranceQuery.getInsured());
            vehicleInsuranceDo.setUnderwritingDate(vehicleInsuranceQuery.getRenewalDate());
            // Convert years to months (e.g., 2 years → 24 months)
            Integer years = vehicleInsuranceQuery.getRenewalYears();
            int months = years * 12;
            // Policy expiry alert date is underwriting date plus (months - 1)
            vehicleInsuranceDo.setPolicyExpiryAlert(
                    vehicleInsuranceDo.getUnderwritingDate().plusMonths(months - 1)
            );
            vehicleInsuranceDo.setUpdateMan(userNo);
            return vehicleInsuranceMapper.updateSelective(vehicleInsuranceDo);
        } else {
            return 0;
        }
    }

    private VehicleInsuranceDO insertHandleDO(VehicleInsuranceDTO vehicleInsuranceDto, String userNo,String batch) {
        VehicleInsuranceDO vehicleInsuranceDo = new VehicleInsuranceDO();
        BeanUtils.copyProperties(vehicleInsuranceDto, vehicleInsuranceDo);
        vehicleInsuranceDo.setPolicyExpiryAlert(vehicleInsuranceDo.getUnderwritingDate().plusMonths(CommonConstant.REMINDER_MONTHS));
        vehicleInsuranceDo.setCreateTime(LocalDateTime.now());
        vehicleInsuranceDo.setUpdateMan(userNo);
        vehicleInsuranceDo.setBatchNo(batch);
        String newCarNumber = vehicleInsuranceDto.getCarNumber().replaceAll("[\\s-]+", "");
        vehicleInsuranceDo.setCarNumber(newCarNumber);
        return vehicleInsuranceDo;

    }

    private String getBatchNo() {
        String localDateString = NumberUtils.formatLocalDateTimeSStringMonth(LocalDateTime.now());
        long number = (long) redisService.incr(RedisKeyNameConstants.VHICLEINSURANCE_NO + localDateString, 1L).getData();
        return NumberUtils.createNumber("hwcb", localDateString, number, MobileMessageCode.ALIYUN_SHORT_MESSAGE_PLATFORM);
    }


    private Map<String, Integer> getcarTypeMap() {

        // 创建一个 HashMap 用于存储车型信息
        Map<String, Integer> carTypeMap = new HashMap<>();
        // 定义车型信息字符串
        String carTypeStr = "1 特三,2 丰田,3 希尔,4 五菱,5 春星,6 货,7 长城货,8 长城,9 红宇,10 鸿星达,11 哈弗,12 霸道,13 雷克萨斯,14 雪佛兰,15 奔驰,16 江特,17 威尔法,18 奥迪,19 比亚迪,20 酷路泽,21 普拉多";
        // 按逗号分割字符串，得到每个车型和对应数字的组合
        String[] carTypePairs = carTypeStr.split(",");

        for (String pair : carTypePairs) {
            // 按空格分割，得到数字和车型名称
            String[] parts = pair.trim().split(" ");
            if (parts.length == 2) {
                try {
                    // 将数字部分转换为整数
                    int carTypeId = Integer.parseInt(parts[0]);
                    // 提取车型名称
                    String carTypeName = parts[1];
                    // 将车型名称和对应的数字存入 Map
                    carTypeMap.put(carTypeName, carTypeId);
                } catch (NumberFormatException e) {
                    System.err.println("在解析数字时出现错误: " + pair);
                }
            }
        }
        return carTypeMap;
    }


}
