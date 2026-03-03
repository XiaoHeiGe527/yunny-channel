package com.yunny.channel.service;

import com.yunny.channel.common.dto.ChemicalFileDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.ChemicalFileVO;
import com.yunny.channel.common.query.ChemicalFileQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * Created by Fe
 */
public interface ChemicalFileService{

    CommonPager<ChemicalFileVO> listByPage(ChemicalFileQuery query);

    /**
    * 主键查询
    * @param id
    * @return
     */
    ChemicalFileVO getById(Long id);

    /**
     * 插入
     * @return
     */
    int insertSelective(ChemicalFileDTO chemicalFileDto);

    /**
     * 修改
     * @return
     */
    int updateSelective(ChemicalFileDTO chemicalFileDto);


    /**
     * 化工档案上传
     * @param file
     * @param query
     * @return
     */
    BaseResult handleFileUpload(MultipartFile file, ChemicalFileQuery query);


    /**
     * 下载水印
     * @param remoteFileName
     * @param watermarkText
     * @param outputStream
     * @return
     */
    boolean addWatermarkAndDownload(String remoteFileName, String watermarkText, OutputStream outputStream);

    /**
     * 下载
     * @param remoteFileName
     * @param response
     * @return
     */
    BaseResult downloadFile(String remoteFileName, HttpServletResponse response);


}
