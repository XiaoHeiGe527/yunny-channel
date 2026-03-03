package com.yunny.channel.mapper;


import com.yunny.channel.common.entity.VehicleInsuranceDO;
import com.yunny.channel.common.query.VehicleInsuranceQuery;

import java.util.List;

/**
 * Created by fe
 */
public interface VehicleInsuranceMapper {

    /**
	* 查询总条数
	* @param vehicleInsuranceQuery
	* @return
	*/
	Long countByQuery(VehicleInsuranceQuery vehicleInsuranceQuery);

	/**
	 * 分页查询
	 * @param vehicleInsuranceQuery
	 * @return
	 */
	List<VehicleInsuranceDO> listByQuery(VehicleInsuranceQuery vehicleInsuranceQuery);


	/**
	 * 查询所有到期的列出来
	 * @param vehicleInsuranceQuery
	 * @return
	 */
	List<VehicleInsuranceDO> listByExpirationReminder(VehicleInsuranceQuery vehicleInsuranceQuery);


	/**
	 * 查询到期的承保车辆数量
	 * @param vehicleInsuranceQuery
	 * @return
	 */
	 Long expirationReminderCount (VehicleInsuranceQuery vehicleInsuranceQuery);


	/**
	 * 查询所有车辆
	 * @param vehicleInsuranceQuery
	 * @return
	 */
	List<VehicleInsuranceDO> listAll(VehicleInsuranceQuery vehicleInsuranceQuery);


	/**
	 * 查询
	 * @param id
	 * @return
	 */
	VehicleInsuranceDO getById(Long id);

	/**
	 * 插入
	 * @param vehicleInsuranceDo
	 * @return
	 */
	int insertSelective(VehicleInsuranceDO vehicleInsuranceDo);


	/**
	 * 批量插入
	 * @param doList
	 * @return
	 */
	int batchInsert(List<VehicleInsuranceDO> doList);



	/**
	 * 更新
	 * @param vehicleInsuranceDo
	 * @return
	 */
	int updateSelective(VehicleInsuranceDO vehicleInsuranceDo);

}