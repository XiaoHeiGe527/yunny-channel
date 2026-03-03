package com.yunny.channel.service;

import com.yunny.channel.common.dto.EmployeeFileDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.EmployeeFileVO;
import com.yunny.channel.common.query.EmployeeFileQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;


/**
 * Created by Fe
 */
public interface EmployeeFileService {

    CommonPager<EmployeeFileVO> listByPage(EmployeeFileQuery query);

    Long countByQuery(EmployeeFileQuery query);


    Long getnameCount(EmployeeFileQuery query);


    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    EmployeeFileVO getById(Integer id);




    /**
     * 插入
     *
     * @return
     */
    int insertSelective(EmployeeFileDTO employeeFileDto);

    /**
     * 修改
     *
     * @return
     */
    int updateSelective(EmployeeFileDTO employeeFileDto);


    /**
     * 上传证件
     *
     * @param file
     * @param query
     * @return
     */
    BaseResult handleFileUpload(MultipartFile file, EmployeeFileQuery query);

    BaseResult replaceUpload(MultipartFile file,Integer id);


    BaseResult downloadFile(String remoteFileName, HttpServletResponse response);

    boolean addWatermarkAndDownload(String remoteFileName, String watermarkText, OutputStream outputStream);


}
