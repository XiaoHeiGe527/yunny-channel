package com.yunny.channel.service.impl;

import com.yunny.channel.common.code.MobileMessageCode;
import com.yunny.channel.common.constant.BusinessCodeConstants;
import com.yunny.channel.common.constant.CommonConstant;
import com.yunny.channel.common.constant.RedisKeyNameConstants;
import com.yunny.channel.common.dto.CompanyVehiclesDTO;
import com.yunny.channel.common.dto.VehiclesOutwardCardDTO;
import com.yunny.channel.common.entity.VehiclesOutwardCardDO;

import com.yunny.channel.common.exception.ServiceException;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.query.CompanyVehiclesQuery;
import com.yunny.channel.common.query.VehicleCustomersQuery;
import com.yunny.channel.common.query.VehicleInsuranceQuery;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.tools.redis.RedisService;
import com.yunny.channel.common.util.NumberUtils;
import com.yunny.channel.common.vo.CompanyVehiclesVO;
import com.yunny.channel.common.vo.VehicleCustomersVO;
import com.yunny.channel.common.vo.VehiclesOutwardCardVO;
import com.yunny.channel.common.query.VehiclesOutwardCardQuery;
import com.yunny.channel.mapper.VehiclesOutwardCardMapper;
import com.yunny.channel.service.CompanyVehiclesService;
import com.yunny.channel.service.VehicleCustomersService;
import com.yunny.channel.service.VehicleInsuranceService;
import com.yunny.channel.service.VehiclesOutwardCardService;
import com.yunny.channel.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.yunny.channel.common.constant.RedisKeyNameConstants.INSERT_USER_INFO_DATA_JOB_CURRENT_PAGE;


/**
 * Created by Fe
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class VehiclesOutwardCardServiceImpl implements VehiclesOutwardCardService {

    @Autowired
    RedisService redisService;

    @Resource
    private VehicleCustomersService vehicleCustomersService;

    @Resource
    private VehicleInsuranceService vehicleInsuranceService;

    @Resource
    private CompanyVehiclesService companyVehiclesService;


    @Resource
    private VehiclesOutwardCardMapper vehiclesOutwardCardMapper;

    /**
     * 分页查询
     *
     * @return
     */
    @Override
    public CommonPager<VehiclesOutwardCardVO> listByPage(VehiclesOutwardCardQuery vehiclesOutwardCardQuery) {

        log.info("vehiclesOutwardCardQuery:[{}]",vehiclesOutwardCardQuery);

        if(vehiclesOutwardCardQuery.getIsReminder().equals("true")){
            vehiclesOutwardCardQuery.setExpiryAlert(TimeUtil.timeFormatString(LocalDateTime.now(), TimeUtil.DATE_TIME_FORMAT));
        }

        PageParameter pageParameter = vehiclesOutwardCardQuery.getPageParameter();
        return new CommonPager<VehiclesOutwardCardVO>(
                new PageParameter(pageParameter.getCurrentPage(), pageParameter.getPageSize(),
                        vehiclesOutwardCardMapper.countByQuery(vehiclesOutwardCardQuery)),
                vehiclesOutwardCardMapper.listByQuery(vehiclesOutwardCardQuery).stream()
                        .map(item -> {
                            VehiclesOutwardCardVO vehiclesOutwardCardVo = new VehiclesOutwardCardVO();
                            BeanUtils.copyProperties(item, vehiclesOutwardCardVo);
                            return vehiclesOutwardCardVo;
                        }).collect(Collectors.toList()));
    }

    /**
     * 通过id获取
     *
     * @return
     */
    @Override
    public VehiclesOutwardCardVO getById(Long id) {
        VehiclesOutwardCardDO vehiclesOutwardCardDo = vehiclesOutwardCardMapper.getById(id);
        if (null == vehiclesOutwardCardDo) {
            return null;
        }
        VehiclesOutwardCardVO vehiclesOutwardCardVo = new VehiclesOutwardCardVO();
        BeanUtils.copyProperties(vehiclesOutwardCardDo, vehiclesOutwardCardVo);
        return vehiclesOutwardCardVo;
    }

    @Override
    public VehiclesOutwardCardVO getByCardNo(String cardNo) {
        VehiclesOutwardCardDO vehiclesOutwardCardDo = vehiclesOutwardCardMapper.getByCardNo(cardNo);
        if (null == vehiclesOutwardCardDo) {
            return null;
        }
        VehiclesOutwardCardVO vehiclesOutwardCardVo = new VehiclesOutwardCardVO();
        BeanUtils.copyProperties(vehiclesOutwardCardDo, vehiclesOutwardCardVo);
        return vehiclesOutwardCardVo;
    }

    /**
     * 新增
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int insertSelective(VehiclesOutwardCardDTO vehiclesOutwardCardDto) {
        Long countcompanyVehicles = companyVehiclesService.countByQuery(CompanyVehiclesQuery.builder().carNumber(vehiclesOutwardCardDto.getCarNumber()).isManage(1)
                .build());

        if (countcompanyVehicles == 0) {
            throw new ServiceException("无效的【" + vehiclesOutwardCardDto.getCarNumber() + "】车牌号，请联系管理员！");
        }


        VehicleCustomersVO vehicleCustomersVO = vehicleCustomersService.getByName(vehiclesOutwardCardDto.getVehicleCustomersName());
        if (vehicleCustomersVO == null) {

            throw new ServiceException("不存在【" + vehiclesOutwardCardDto.getVehicleCustomersName() + "】客户，请联系管理员！");
        }

        vehiclesOutwardCardDto.setVehicleCustomersId(vehicleCustomersVO.getId());

        //生成开车卡号 获取redis序号（每天从1开始的）
        String localDateString = NumberUtils.formatLocalDateTimeSStringMonth(LocalDateTime.now());
        long number = (long) redisService.incr(RedisKeyNameConstants.INSERT_VEHICLESOUTWARDCARD_NO + localDateString, 1L).getData();
        String cardNo = NumberUtils.createNumber("hw", localDateString, number, MobileMessageCode.ALIYUN_SHORT_MESSAGE_PLATFORM);
        vehiclesOutwardCardDto.setCardNo(cardNo);

        //到期时间 开卡时间+卡剩余天数
        //vehiclesOutwardCardDto.setExpirationDate(vehiclesOutwardCardDto.getOpenDate().plusDays(vehiclesOutwardCardDto.getDaysRemaining()));
        //到期时间提前5天提醒
        //vehiclesOutwardCardDto.setWarningTime(vehiclesOutwardCardDto.getExpirationDate().minusDays(CommonConstant.VEHICLESOUTWARD_CARD_WARNING_DAY));

        vehiclesOutwardCardDto.setWarningTime(vehiclesOutwardCardDto.getExpirationDate().minusDays(CommonConstant.VEHICLESOUTWARD_CARD_WARNING_DAY));
        //状态1可用
        vehiclesOutwardCardDto.setState(1);


        VehiclesOutwardCardDO vehiclesOutwardCardDo = new VehiclesOutwardCardDO();
        BeanUtils.copyProperties(vehiclesOutwardCardDto, vehiclesOutwardCardDo);
        //vehiclesOutwardCardDo.setCreateTime(LocalDateTime.now());
        //log.info("vehiclesOutwardCardDo====[{}]",vehiclesOutwardCardDo);
        return vehiclesOutwardCardMapper.insertSelective(vehiclesOutwardCardDo);
    }

    /**
     * 修改
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int updateSelective(VehiclesOutwardCardDTO vehiclesOutwardCardDto) {
        VehiclesOutwardCardDO vehiclesOutwardCardDo = new VehiclesOutwardCardDO();
        BeanUtils.copyProperties(vehiclesOutwardCardDto, vehiclesOutwardCardDo);
        return vehiclesOutwardCardMapper.updateSelective(vehiclesOutwardCardDo);
    }

    @Override
    public BaseResult cardDepart(VehiclesOutwardCardDTO vehiclesOutwardCardDto) {
        CompanyVehiclesVO companyVehiclesVO = companyVehiclesService.getByCarNumber(vehiclesOutwardCardDto.getCarNumber());
        if (companyVehiclesVO == null) {

            throw new ServiceException("不存在【" + vehiclesOutwardCardDto.getCarNumber() + "】车牌号，请联系管理员！");
        }

        if(companyVehiclesVO.getActiveState()==2){

            throw new ServiceException("车辆【" + vehiclesOutwardCardDto.getCarNumber() + "】已经发车了！请先去车辆列表还车！");
        }

        VehiclesOutwardCardDO vehiclesOutwardCardVO = vehiclesOutwardCardMapper.getByCardNo(vehiclesOutwardCardDto.getCardNo());

        if (vehiclesOutwardCardVO == null||vehiclesOutwardCardVO.getState()==0) {
            throw new ServiceException("【" + vehiclesOutwardCardDto.getCardNo() + "】卡号错误的操作，请联系管理员！");
        }

        //状态已发车

        companyVehiclesService.updateSelectiveByCarNumber(CompanyVehiclesDTO.builder().activeState(2).updateTime(LocalDateTime.now())
                .carNumber(companyVehiclesVO.getCarNumber()).build());

        //卡正外出中
        vehiclesOutwardCardMapper.updateSelectiveByCardNo(VehiclesOutwardCardDO.builder().state(3).updateTime(LocalDateTime.now())
                .carNumber(companyVehiclesVO.getCarNumber())
                .cardNo(vehiclesOutwardCardVO.getCardNo()).build());
        //新增一条发车记录表

        return BaseResult.success();
    }



    @Override
    public Long expirationReminderCount() {
        return vehiclesOutwardCardMapper.countByQuery(VehiclesOutwardCardQuery.builder()
                .expiryAlert(TimeUtil.timeFormatString(LocalDateTime.now(), TimeUtil.DATE_TIME_FORMAT))
                .state(1)
                .build());
    }



    @Override
    public Long countByQuery(VehiclesOutwardCardQuery query){
        return vehiclesOutwardCardMapper.countByQuery(query);
    }


    /**
     * 处理到期的卡
     * @return
     */
    @Transactional
    @Override
    public BaseResult handleExpireCard() {
        //当前时间到期的 每次50条
        List<VehiclesOutwardCardDO> vehiclesOutwardCardDOS = vehiclesOutwardCardMapper.listByQuery(    VehiclesOutwardCardQuery.builder()
                .pageParameter(new PageParameter(0, 50))
                .expiredAlready(TimeUtil.timeFormatString(LocalDateTime.now(), TimeUtil.DATE_TIME_FORMAT))
                .state(1).build());
        //设置过期
        for (VehiclesOutwardCardDO vehiclesOutwardCardDO:vehiclesOutwardCardDOS ) {
            vehiclesOutwardCardDO.setState(2);
            //count += vehiclesOutwardCardMapper.updateSelectiveByCardNo(vehiclesOutwardCardDO);
        }
        int count = 0;
        // 批量更新
        if (!vehiclesOutwardCardDOS.isEmpty()) {
            count += vehiclesOutwardCardMapper.batchUpdateVehiclesOutwardCard(vehiclesOutwardCardDOS);
        }

        return BaseResult.success(count);
    }

}
