package com.yunny.channel.service.impl;

import cn.hutool.core.io.FileUtil;
import com.yunny.channel.common.constant.SystemConstant;
import com.yunny.channel.common.entity.ChemicalFileDO;

import com.yunny.channel.common.dto.ChemicalFileDTO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.util.PdfUtil;
import com.yunny.channel.common.vo.ChemicalFileVO;
import com.yunny.channel.common.query.ChemicalFileQuery;

import com.yunny.channel.mapper.ChemicalFileMapper;
import com.yunny.channel.service.ChemicalFileService;
import com.yunny.channel.service.ftp.FtpService;
import com.yunny.channel.util.BeanConvertUtil;
import com.yunny.channel.util.FtpOperationUtil;
import com.yunny.channel.util.SmartFtpUtil;
import com.yunny.channel.util.TempFileManager;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;


/**
 * Created by Fe
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class ChemicalFileServiceImpl implements ChemicalFileService {

    @Resource
    private ChemicalFileMapper chemicalFileMapper;
    @Autowired
    private FtpService ftpService;
    @Autowired
    private FtpOperationUtil ftpOperationUtil; // 注入FTP工具类

    @Value("${pdf.temp-dir:/temp/pdf-watermark/}")
    private String tempDir;


    @Override
    public CommonPager<ChemicalFileVO> listByPage(ChemicalFileQuery query) {
        PageParameter page = query.getPageParameter();
        return new CommonPager<>(
                new PageParameter(page.getCurrentPage(), page.getPageSize(), chemicalFileMapper.countByQuery(query)),
                BeanConvertUtil.convertList(chemicalFileMapper.listByQuery(query), ChemicalFileVO.class)
        );
    }

    @Override
    public ChemicalFileVO getById(Long id) {
        return BeanConvertUtil.convert(chemicalFileMapper.getById(id), ChemicalFileVO.class);
    }

    @Override
    public int insertSelective(ChemicalFileDTO dto) {
        return chemicalFileMapper.insertSelective(BeanConvertUtil.convert(dto, ChemicalFileDO.class));
    }

    @Override
    public int updateSelective(ChemicalFileDTO dto) {
        return chemicalFileMapper.updateSelective(BeanConvertUtil.convert(dto, ChemicalFileDO.class));
    }

    @Override
    public BaseResult handleFileUpload(MultipartFile file, ChemicalFileQuery query) {
        String remotePath = SystemConstant.FTP_CHEMICAL_PATH + query.getPdfFileUrl();
        try (InputStream is = file.getInputStream()) {
            boolean success = ftpOperationUtil.uploadFromStream(is, remotePath);
            if (success) {
                chemicalFileMapper.insertSelective(ChemicalFileDO.builder()
                        .fileType(query.getFileType())
                        .fileTypeCode(query.getFileTypeCode())
                        .typeCode(query.getTypeCode())
                        .documentType(query.getDocumentType())
                        .documentCode(query.getDocumentCode())
                        .documentTitle(query.getDocumentTitle())
                        .documentContent(query.getDocumentContent())
                        .signatureTime(query.getSignatureTime())
                        .placeTime(query.getPlaceTime())
                        .expirationDate(query.getExpirationDate())
                        .positionCode(query.getPositionCode())
                        .documentDescribe(query.getDocumentDescribe())
                        .state(query.getState())
                        .remarks(query.getRemarks())
                        .pdfFileUrl(query.getPdfFileUrl())
                        .updateMan(query.getUpdateMan())
                        .build());
                return BaseResult.success("上传成功");
            }
            return BaseResult.failure(500, "上传失败");
        } catch (Exception e) {
            log.error("文件上传异常", e);
            return BaseResult.failure(500, "上传异常");
        }
    }

    @Override
    public boolean addWatermarkAndDownload(String remoteFileName, String watermarkText, OutputStream outputStream) {
        String remoteFilePath = SystemConstant.FTP_CHEMICAL_PATH + remoteFileName;
        String tempFilePath = tempDir + remoteFileName;
        String watermarkedPath = tempFilePath.replace(".pdf", "_watermark.pdf");

        try {
            // 创建临时目录
            FileUtil.mkdir(tempDir);

            // 下载原始文件
            if (!ftpOperationUtil.downloadToLocal(remoteFilePath, tempFilePath)) {
                log.error("PDF下载失败：{}", remoteFilePath);
                return false;
            }

            // 添加水印
            if (PdfUtil.addWatermark(tempFilePath, watermarkText, watermarkedPath) == null) {
                log.error("水印添加失败：{}", remoteFileName);
                return false;
            }

            // 输出带水印文件
            try (FileInputStream is = new FileInputStream(watermarkedPath)) {
                IOUtils.copy(is, outputStream); // 使用commons-io的IOUtils简化流复制
                outputStream.flush();
            }
            return true;

        } catch (Exception e) {
            log.error("水印处理失败", e);
            return false;
        } finally {
            // 清理临时文件
            TempFileManager.cleanTempFiles(tempFilePath, watermarkedPath);
        }
    }

    @Override
    public BaseResult downloadFile(String remoteFileName, HttpServletResponse response) {
        boolean success = ftpService.downloadFile(remoteFileName, response, SystemConstant.FTP_CHEMICAL_PATH);
        return success ? BaseResult.success("下载成功") : BaseResult.failure(500, "下载失败");
    }
}
