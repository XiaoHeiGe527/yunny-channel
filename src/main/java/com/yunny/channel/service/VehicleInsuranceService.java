package com.yunny.channel.service;

import com.yunny.channel.common.dto.VehicleInsuranceDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.VehicleInsuranceVO;
import com.yunny.channel.common.query.VehicleInsuranceQuery;
import org.apache.bcel.verifier.statics.LONG_Upper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


/**
 * Created by Fe
 */
public interface VehicleInsuranceService{

    CommonPager<VehicleInsuranceVO> listByPage(VehicleInsuranceQuery query);


    /**
     * 查询11月快到期的提醒
     * @param vehicleInsuranceQuery
     * @return
     */
    List<VehicleInsuranceVO> listByExpirationReminder(VehicleInsuranceQuery vehicleInsuranceQuery);

    /**
     * 查询所有车辆
     * @param vehicleInsuranceQuery
     * @return
     */
    List<VehicleInsuranceVO> listAll(VehicleInsuranceQuery vehicleInsuranceQuery);


    /**
    * 主键查询
    * @param id
    * @return
     */
    VehicleInsuranceVO getById(Long id);

    /**
     * 插入
     * @return
     */
    int insertSelective(VehicleInsuranceDTO vehicleInsuranceDto, String userN);

    /**
     * 修改
     * @return
     */
    int updateSelective(VehicleInsuranceDTO vehicleInsuranceDto);


    /**
     * 批量插入
     * @param dtoList
     * @return
     */
    Map<String,String> batchInsert(List<VehicleInsuranceDTO> dtoList, String userNo);

    /**
     * 续保
     * @param vehicleInsuranceQuery
     * @return
     */
    int renewInsurance(VehicleInsuranceQuery vehicleInsuranceQuery, String userNo) ;

    /**
     * 到期数量
     * @param vehicleInsuranceQuery
     * @return
     */
   Long expirationReminderCount(VehicleInsuranceQuery vehicleInsuranceQuery);


    /**
     * Excel导入
     */
    Map<String,String> importVehicleInsuranceExcel(MultipartFile file);

    /**
     * 处理数据库车辆信息
     * @return
     */
    BaseResult handleCard();


    Long countByQuery(VehicleInsuranceQuery vehicleInsuranceQuery);

}
