package com.yunny.channel.mapper;

import com.yunny.channel.common.entity.VehicleCustomersDO;
import com.yunny.channel.common.dto.VehicleCustomersDTO;
import com.yunny.channel.common.query.VehicleCustomersQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by fe
 */
public interface VehicleCustomersMapper {

    /**
	* 查询总条数
	* @param vehicleCustomersQuery
	* @return
	*/
	Long countByQuery(VehicleCustomersQuery vehicleCustomersQuery);

	/**
	 * 分页查询
	 * @param vehicleCustomersQuery
	 * @return
	 */
	List<VehicleCustomersDO> listByQuery(VehicleCustomersQuery vehicleCustomersQuery);

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	VehicleCustomersDO getById(Integer id);

	/**
	 * 插入
	 * @param vehicleCustomersDo
	 * @return
	 */
	int insertSelective(VehicleCustomersDO vehicleCustomersDo);

	/**
	 * 更新
	 * @param vehicleCustomersDo
	 * @return
	 */
	int updateSelective(VehicleCustomersDO vehicleCustomersDo);


	/**
	 * 查询所有车辆
	 * @param vehicleCustomersQuery
	 * @return
	 */
	List<VehicleCustomersDO> listAll(VehicleCustomersQuery vehicleCustomersQuery);


	/**
	 * 根据用户名称获得用户
	 * @param name
	 * @return
	 */
	VehicleCustomersDO getByName(String name);


}