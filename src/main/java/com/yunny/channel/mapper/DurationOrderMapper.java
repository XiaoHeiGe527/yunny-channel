package com.yunny.channel.mapper;


import com.yunny.channel.common.dto.OperationSwitchDTO;
import com.yunny.channel.common.entity.DurationOrderDO;
import com.yunny.channel.common.entity.DurationOrderDO2;
import com.yunny.channel.common.entity.DurationOrderJoinAdOrderDO;
import com.yunny.channel.common.entity.DurationOrderSFWDO;
import com.yunny.channel.common.query.DurationOrderQuery;
import com.yunny.channel.common.query.DurationQuery;
import com.yunny.channel.common.query.ProductInfoDmmQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Mr. Du
 * @explain
 * @createTime 2199/12/26 18:22
 * @motto If you would have leisure, do not waste it.
 */

public interface DurationOrderMapper {


    /**
     * 插入
     *
     * @return
     */
    int insertSelective(DurationOrderDO durationOrderDO);

    /**
     * 查询时长订单
     *
     * @param durationQuery
     * @return
     */
    List<DurationOrderDO> selectByDeviceNo(DurationQuery durationQuery);

    /**
     * 根据 订单编号 唯一编号查询时长订单表
     *
     * @param orderNo
     * @return
     */
    DurationOrderDO getByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 根据 设备号查询时长订单表
     *
     * @param deviceNo
     * @return
     */
    List<DurationOrderDO> getByDeviceNo(@Param("deviceNo") String deviceNo);

    /**
     * 根据状态 查询时长订单
     *
     * @param state 1 有效 0失效
     * @return
     */
    List<DurationOrderDO> getDurationOrderListByState(@Param("state") Integer state);

    /**
     * 根据ID修改时长订单（当前MAP仅支持修改状态）
     *
     * @param id
     * @return
     */
    int updateInvalidByById(@Param("id") Long id);


    /**
     * 根据订单编号集合查询 时长订单
     *
     * @param orderNoList
     * @return
     */
    List<DurationOrderDO> queryByOrderNoList(@Param("list") List<String> orderNoList);


    /**
     * 根据ID修改时长订单（当前MAP仅支持修改状态）
     *
     * @param operationSwitchDTO
     * @return
     */
    int updateStateByOrderNo(OperationSwitchDTO operationSwitchDTO);

    /**
     * 根据订单号更新时长订单
     *
     * @param durationOrderDO
     */
    void updateByOrderNo(DurationOrderDO durationOrderDO);


    DurationOrderDO getById(@Param("id") Long id);

    /**
     * 查询用户主机
     *
     * @param productInfoDmmQuery
     * @return
     */
    List<DurationOrderDO2> selectByList(ProductInfoDmmQuery productInfoDmmQuery);

    /**
     * 统计
     *
     * @param productInfoDmmQuery
     * @return
     */
    Integer selectByListCount(ProductInfoDmmQuery productInfoDmmQuery);

    List<DurationOrderDO> findAllByUserNo(String userNo);

    /**
     * 查询时长订单为结束 AD订单虚机未销毁的记录
     *
     * @param state
     * @return
     */
    List<DurationOrderJoinAdOrderDO> getDurationOrderJoinAdOrderListByState(@Param("state") Integer state);


    /**
     * 根据订单编号与用户号 查询时长订单
     * @param orderNo
     * @return
     */
    DurationOrderDO selectDurationOrder(@Param("orderNo") String orderNo);

    /**
     * 根据 订单编号 唯一编号查询时长订单表 不判断状态
     *
     * @param orderNo
     * @return
     */
    DurationOrderDO getByOnlyOrder(@Param("orderNo") String orderNo);


    /**
     * 根据 状态集合 查询 时长订单
     * @param stateList
     * @return
     */
    List<DurationOrderDO> getByStateList(@Param("stateList") List<Integer> stateList);


    /**
     * 查询到期时间小于某时间的时长订单 (不查过期的 到期时间小于当前MYsql now()函数 时间的)
     * @param query
     * @return
     */
    List<DurationOrderSFWDO> getByStateListAndDueTime(DurationOrderQuery query);
}