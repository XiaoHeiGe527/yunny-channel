package com.yunny.channel.mapper;

import com.yunny.channel.common.entity.VehiclesOutwardCardDO;
import com.yunny.channel.common.dto.VehiclesOutwardCardDTO;
import com.yunny.channel.common.query.VehiclesOutwardCardQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by fe
 */
public interface VehiclesOutwardCardMapper {

    /**
	* 查询总条数
	* @param vehiclesOutwardCardQuery
	* @return
	*/
	Long countByQuery(VehiclesOutwardCardQuery vehiclesOutwardCardQuery);

	/**
	 * 分页查询
	 * @param vehiclesOutwardCardQuery
	 * @return
	 */
	List<VehiclesOutwardCardDO> listByQuery(VehiclesOutwardCardQuery vehiclesOutwardCardQuery);

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	VehiclesOutwardCardDO getById(Long id);

	VehiclesOutwardCardDO getByCardNo(String cardNo);



	/**
	 * 插入
	 * @param vehiclesOutwardCardDo
	 * @return
	 */
	int insertSelective(VehiclesOutwardCardDO vehiclesOutwardCardDo);

	/**
	 * 更新
	 * @param vehiclesOutwardCardDo
	 * @return
	 */
	int updateSelective(VehiclesOutwardCardDO vehiclesOutwardCardDo);

	int updateSelectiveByCardNo(VehiclesOutwardCardDO vehiclesOutwardCardDo);

	int batchUpdateVehiclesOutwardCard(@Param("list") List<VehiclesOutwardCardDO> vehiclesOutwardCardDOS);

}