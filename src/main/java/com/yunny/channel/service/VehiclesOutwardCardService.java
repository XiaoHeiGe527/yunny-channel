package com.yunny.channel.service;

import com.yunny.channel.common.dto.VehiclesOutwardCardDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.VehiclesOutwardCardVO;
import com.yunny.channel.common.query.VehiclesOutwardCardQuery;


/**
 * Created by Fe
 */
public interface VehiclesOutwardCardService{

    CommonPager<VehiclesOutwardCardVO> listByPage(VehiclesOutwardCardQuery query);

    /**
    * 主键查询
    * @param id
    * @return
     */
    VehiclesOutwardCardVO getById(Long id);

    VehiclesOutwardCardVO getByCardNo(String cardNo);

    /**
     * 插入
     * @return
     */
    int insertSelective(VehiclesOutwardCardDTO vehiclesOutwardCardDto);

    /**
     * 修改
     * @return
     */
    int updateSelective(VehiclesOutwardCardDTO vehiclesOutwardCardDto);

    /**
     * 发车
     * @param vehiclesOutwardCardDto
     * @return
     */
    BaseResult cardDepart(VehiclesOutwardCardDTO vehiclesOutwardCardDto);

    /**
     * 查询快到期的可用卡
     * @return
     */
    Long expirationReminderCount();

    Long countByQuery(VehiclesOutwardCardQuery query);


    /**
     * 将可用过期的卡设置为到期的卡
     * @return
     */
    BaseResult handleExpireCard();

}
