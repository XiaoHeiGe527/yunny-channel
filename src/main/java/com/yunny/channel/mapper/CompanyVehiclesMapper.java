package com.yunny.channel.mapper;

import com.yunny.channel.common.entity.CompanyVehiclesDO;
import com.yunny.channel.common.dto.CompanyVehiclesDTO;
import com.yunny.channel.common.query.CompanyVehiclesQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by fe
 */
public interface CompanyVehiclesMapper {

    /**
	* 查询总条数
	* @param companyVehiclesQuery
	* @return
	*/
	Long countByQuery(CompanyVehiclesQuery companyVehiclesQuery);

	/**
	 * 分页查询
	 * @param companyVehiclesQuery
	 * @return
	 */
	List<CompanyVehiclesDO> listByQuery(CompanyVehiclesQuery companyVehiclesQuery);

	/**
	 * 查询集合不分页
	 * @param companyVehiclesQuery
	 * @return
	 */
	List<CompanyVehiclesDO> listAllByQuery(CompanyVehiclesQuery companyVehiclesQuery);


	/**
	 * 查询
	 * @param id
	 * @return
	 */
	CompanyVehiclesDO getById(Long id);


	/**
	 * 根据车牌号查询车辆信息
	 * @param carNumber
	 * @return
	 */
	CompanyVehiclesDO getByCarNumber(String carNumber);


	/**
	 * 插入
	 * @param companyVehiclesDo
	 * @return
	 */
	int insertSelective(CompanyVehiclesDO companyVehiclesDo);

	/**
	 * 更新
	 * @param companyVehiclesDo
	 * @return
	 */
	int updateSelective(CompanyVehiclesDO companyVehiclesDo);

	int updateSelectiveByCarNumber(CompanyVehiclesDO companyVehiclesDo);

	/**
	 * 查询是否存在此车牌的车
	 * @param companyVehiclesQuery
	 * @return
	 */
	Long countCarNumberByQuery(CompanyVehiclesQuery companyVehiclesQuery);


}