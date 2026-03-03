package com.yunny.channel.service.impl;

import cn.hutool.core.io.FileUtil;
import com.yunny.channel.common.constant.SystemConstant;
import com.yunny.channel.common.entity.EmployeeFileDO;
import com.yunny.channel.common.dto.EmployeeFileDTO;
import com.yunny.channel.common.exception.ServiceException;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.util.PdfUtil;
import com.yunny.channel.common.vo.EmployeeFileVO;
import com.yunny.channel.common.query.EmployeeFileQuery;

import com.yunny.channel.mapper.EmployeeFileMapper;
import com.yunny.channel.service.EmployeeFileService;
import com.yunny.channel.service.ftp.FtpService;
import com.yunny.channel.util.BeanConvertUtil;
import com.yunny.channel.util.SmartFtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.stream.Collectors;


/**
 * Created by Fe
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class EmployeeFileServiceImpl implements EmployeeFileService {

    @Resource
    private EmployeeFileMapper employeeFileMapper;

    @Autowired
    private FtpService ftpService;

    // 注入临时目录配置（从 application.properties 读取）
    @Value("${pdf.temp-dir:/temp/pdf-watermark/}")
    private String tempDir;


    // 从 Spring 配置中重新获取 FTP 参数（需在服务层注入配置）
    @Value("${ftp.server.host}")
    private String ftpHost;
    @Value("${ftp.server.port}")
    private int ftpPort;
    @Value("${ftp.server.username}")
    private String ftpUsername;
    @Value("${ftp.server.password}")
    private String ftpPassword;
    @Value("${ftp.server.remote-directory}")
    private String ftpRemoteDirectory;



    /**
     * 添加水印并下载（核心业务逻辑）
     */
    @Override
    public boolean addWatermarkAndDownload(String remoteFileName, String watermarkText, OutputStream outputStream
    ) {
        // 关键修改：拼接完整远程路径（与原有接口一致）
        String remoteFilePath = SystemConstant.FTP_EMPLOYEE_PATH + remoteFileName; // 例如 "ftp-path/文件.pdf"
        String tempFilePath = tempDir + remoteFileName;
        String watermarkedPath = tempFilePath.replace(".pdf", "_watermark.pdf");
        try {
            // 1. 确保临时目录存在
            File tempDirFile = new File(tempDir);
            if (!tempDirFile.exists() && !tempDirFile.mkdirs()) {
                log.error("临时目录创建失败: {}", tempDir);
                return false;
            }

            // 2. 下载原始文件（使用完整路径）
            try (FileOutputStream fileOutputStream = new FileOutputStream(tempFilePath)) {
                SmartFtpUtil ftpUtil = new SmartFtpUtil(
                        ftpHost, ftpPort, ftpUsername, ftpPassword, ftpRemoteDirectory
                );
                // 传入完整路径（remoteFilePath）而非仅文件名
                if (!ftpUtil.downloadFile(remoteFilePath, fileOutputStream)) {
                    log.error("原始文件下载失败: {}", remoteFilePath); // 打印完整路径便于排查
                    return false;
                }
            }


            // 3. 添加水印
            if (PdfUtil.addWatermark(tempFilePath, watermarkText, watermarkedPath) == null) {
                log.error("水印添加失败: {}", remoteFileName);
                return false;
            }

            // 4. 流传输带水印文件（可直接写入 response 或 FTP）
            try (FileInputStream inputStream = new FileInputStream(watermarkedPath)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
            }
            return true;

        } catch (Exception e) {
            log.error("水印处理失败: {}", e.getMessage(), e);
            return false;
        } finally {
            // 5. 清理临时文件
            // 推荐写法：逐个删除（代码清晰，避免 IDE 误判）
            FileUtil.del(tempFilePath);
            FileUtil.del(watermarkedPath);
        }
    }



    /**
     * 分页查询
     * @return
     */
    @Override
    public CommonPager<EmployeeFileVO> listByPage(EmployeeFileQuery employeeFileQuery) {
        // 1. 处理分页参数（若为null则初始化默认值）
        PageParameter pageParameter = employeeFileQuery.getPageParameter();
        if (pageParameter == null) {
            // 默认为第1页，每页10条（可根据业务调整默认值）
            pageParameter = new PageParameter(1, 10);
            employeeFileQuery.setPageParameter(pageParameter); // 回设到查询对象中，确保 mapper 能获取到分页参数
        }

        // 2. 执行查询并返回结果
        return new CommonPager<EmployeeFileVO>(
                new PageParameter(
                        pageParameter.getCurrentPage(),
                        pageParameter.getPageSize(),
                        employeeFileMapper.countByQuery(employeeFileQuery) // 总条数
                ),
                employeeFileMapper.listByQuery(employeeFileQuery).stream()
                        .map(item -> BeanConvertUtil.convert(item, EmployeeFileVO.class)) // 复用转换工具类
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Long countByQuery(EmployeeFileQuery query) {
        return employeeFileMapper.countByQuery(query);
    }

    @Override
    public Long getnameCount(EmployeeFileQuery query) {
        return employeeFileMapper.getnameCount(query);
    }

    /**
     * 通过id获取
     * @return
     */
    @Override
    public EmployeeFileVO getById(Integer id) {
        EmployeeFileDO employeeFileDo = employeeFileMapper.getById(id);
        if(null == employeeFileDo){
            return null;
        }
        EmployeeFileVO employeeFileVo = new EmployeeFileVO();
        BeanUtils.copyProperties(employeeFileDo, employeeFileVo);
        return employeeFileVo;
    }




    @Override
    public BaseResult handleFileUpload(MultipartFile file, EmployeeFileQuery query) {

        Long count = employeeFileMapper.countByQuery(EmployeeFileQuery.builder().employeeCodeOnly(query.getEmployeeCode()).build());

        if(count>0L){
            throw new ServiceException("该员工号码"+query.getEmployeeCode()+"已存在，不可以重复增加");
        }

        boolean   ftpResult = false;
        ftpResult = ftpService.uploadFileToFtp(file, SystemConstant.FTP_EMPLOYEE_PATH+query.getPdfFileUrl());

        if(ftpResult){
            employeeFileMapper.insertSelective(EmployeeFileDO.builder()
                    .employeeCode(query.getEmployeeCode())
                    .name(query.getName())
                    .department(query.getDepartment())
                    .rank(query.getRank())
                    .profession(query.getProfession())
                    .positionCode(query.getPositionCode())
                    .state(query.getState())
                    .remarks(query.getRemarks())
                    .pdfFileUrl(query.getPdfFileUrl())
                    .updateMan(query.getUpdateMan())
                    .build());
            return BaseResult.success("上传成功");
        }

        return BaseResult.failure(500,"上传失败");

    }


    @Override
    public BaseResult replaceUpload(MultipartFile file, Integer id) {

        EmployeeFileDO employeeFileDO = employeeFileMapper.getById(id);

        boolean   ftpResult = false;
        ftpResult = ftpService.uploadFileToFtp(file, SystemConstant.FTP_EMPLOYEE_PATH+employeeFileDO.getPdfFileUrl());

        if(ftpResult){

            return BaseResult.success("更改成功");
        }

        return BaseResult.failure(500,"更改失败");
    }



    @Override
    public BaseResult downloadFile(String remoteFileName, HttpServletResponse response) {

        boolean   ftpResult = false;
        ftpResult = ftpService.downloadFile(remoteFileName,response,SystemConstant.FTP_EMPLOYEE_PATH);

        if(ftpResult){

            return BaseResult.success("下载成功");
        }

        return BaseResult.failure(500,"下载失败");

    }


    // ---------------------- 新增----------------------
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int insertSelective(EmployeeFileDTO employeeFileDto) {
        EmployeeFileDO employeeFileDo = new EmployeeFileDO();
        BeanUtils.copyProperties(employeeFileDto, employeeFileDo);
        return employeeFileMapper.insertSelective(employeeFileDo);
    }

    // ---------------------- 修改时校验 employeeCode 唯一性 ----------------------
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int updateSelective(EmployeeFileDTO employeeFileDto) {
        // 1. 校验 employeeCode 是否与其他记录冲突（排除当前记录的 ID）
        checkEmployeeCodeUnique(employeeFileDto.getId(), employeeFileDto.getEmployeeCode());

        EmployeeFileDO employeeFileDo = new EmployeeFileDO();
        BeanUtils.copyProperties(employeeFileDto, employeeFileDo);
        return employeeFileMapper.updateSelective(employeeFileDo);
    }

    // ---------------------- 通用唯一性校验方法 ----------------------
    private void checkEmployeeCodeUnique(Long currentId, String employeeCode) {
        if (employeeCode == null) {
            throw new ServiceException("员工号码不能为空");
        }

        EmployeeFileQuery query = new EmployeeFileQuery();
        query.setEmployeeCode(employeeCode);
        query.setExcludeId(currentId); // 使用 Lombok 自动生成的 setter 方法

        Long count = employeeFileMapper.countByEmployeeCode(query);
        if (count > 0) {
            throw new ServiceException("该员工号码已存在，不可以" + (currentId == null ? "添加" : "修改"));
        }
    }


}
