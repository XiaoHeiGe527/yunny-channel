package com.yunny.channel.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import com.yunny.channel.common.constant.SystemConstant;
import com.yunny.channel.common.dto.EmployeeImagesDTO;
import com.yunny.channel.common.entity.EmployeeFileDO;
import com.yunny.channel.common.entity.EmployeeImagesDO;
import com.yunny.channel.common.exception.ServiceException;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.query.EmployeeFileQuery;
import com.yunny.channel.common.query.EmployeeImagesQuery;
import com.yunny.channel.common.util.PdfUtil;
import com.yunny.channel.common.vo.EmployeeFileVO;
import com.yunny.channel.common.vo.EmployeeImagesVO;
import com.yunny.channel.common.vo.SystemUserVO;
import com.yunny.channel.mapper.EmployeeFileMapper;
import com.yunny.channel.mapper.EmployeeImagesMapper;
import com.yunny.channel.service.EmployeeFileService;
import com.yunny.channel.service.EmployeeImagesService;
import com.yunny.channel.service.SystemUserService;
import com.yunny.channel.util.BeanConvertUtil;
import com.yunny.channel.util.FtpOperationUtil;
import com.yunny.channel.util.TempFileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * 员工PDF图片关联服务实现类
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class EmployeeImagesServiceImpl implements EmployeeImagesService {


    @Autowired
    private SystemUserService systemUserService;

    @Resource
    private EmployeeImagesMapper employeeImagesMapper;

    @Autowired
    private EmployeeFileService employeeFileService;

    @Resource
    private EmployeeFileMapper employeeFileMapper; // 用于员工存在性校验

    @Autowired
    private FtpOperationUtil ftpOperationUtil;

    // Nginx配置（图片访问路径）
    @Value("${nginx.server.host}")
    private String nginxHost;

    @Value("${nginx.server.port}")
    private int nginxPort;

    @Value("${nginx.server.employeeFileImages}")
    private String nginxEmployeeImagesPath;


    /**
     * 分页查询员工图片记录
     */
    @Override
    public CommonPager<EmployeeImagesVO> listByPage(EmployeeImagesQuery query) {
        PageParameter page = query.getPageParameter();
        if (page == null) {
            page = new PageParameter(query.getCurrentPage(), query.getPageSize());
            query.setPageParameter(page);
        }
        return new CommonPager<>(
                new PageParameter(page.getCurrentPage(), page.getPageSize(), employeeImagesMapper.countByQuery(query)),
                BeanConvertUtil.convertList(employeeImagesMapper.listByQuery(query), EmployeeImagesVO.class)
        );
    }

    /**
     * 通过ID获取单条图片记录
     */
    @Override
    public EmployeeImagesVO getById(Long id) {
        return BeanConvertUtil.convert(employeeImagesMapper.getById(id), EmployeeImagesVO.class);
    }

    /**
     * 新增员工图片记录
     */
    @Override
    public int insertSelective(EmployeeImagesDTO dto) {
        return employeeImagesMapper.insertSelective(BeanConvertUtil.convert(dto, EmployeeImagesDO.class));
    }

    /**
     * 更新员工图片记录
     */
    @Override
    public int updateSelective(EmployeeImagesDTO dto) {
        return employeeImagesMapper.updateSelective(BeanConvertUtil.convert(dto, EmployeeImagesDO.class));
    }

    /**
     * 核心方法：根据员工编号获取图片（不存在则生成）
     * 逻辑流程：
     * 1. 校验员工编号是否为空 → 为空则报错
     * 2. 校验员工是否存在（通过EmployeeFileMapper查询文件记录）→ 不存在则报错
     * 3. 查询已有图片记录 → 存在则直接返回
     * 4. 无图片记录则查询员工PDF文件 → 无PDF则报错
     * 5. 有PDF则执行转换流程（下载→加水印→转图片→上传→存库）→ 返回结果
     */
    @Override
    public List<EmployeeImagesVO> listByEmployeeCode(EmployeeImagesQuery query) {
        String employeeCode = query.getEmployeeCode();
        if (employeeCode == null || employeeCode.trim().isEmpty()) {
            log.error("员工编号不能为空");
            throw new ServiceException("员工编号不能为空");
        }

        // 1. 校验员工是否存在（通过员工文件记录判断：有文件则认为员工存在）
        EmployeeFileQuery fileCheckQuery = new EmployeeFileQuery();
        fileCheckQuery.setEmployeeCodeOnly(query.getEmployeeCode());

        Long employeeFileCount = employeeFileMapper.countByQuery(fileCheckQuery);
        if (employeeFileCount == null || employeeFileCount == 0) {
            String errorMsg = "员工不存在，编号：" + employeeCode;
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        }

        // 2. 查询该员工已有的图片记录，存在则直接返回
        List<EmployeeImagesDO> existingDOs = employeeImagesMapper.listByQuery(query);
        if (CollectionUtil.isNotEmpty(existingDOs)) {
            log.info("员工已有图片记录，编号：{}，数量：{}", employeeCode, existingDOs.size());
            return BeanConvertUtil.convertList(existingDOs, EmployeeImagesVO.class);
        }

        // 3. 无图片记录，查询员工PDF文件
        EmployeeFileVO employeeFileVO = getEmployeeFileByCode(employeeCode);
        if (employeeFileVO == null) {
            String errorMsg = "员工存在，但未找到对应的PDF文件，无法生成图片，编号：" + employeeCode;
            log.warn(errorMsg);
            throw new ServiceException(errorMsg);
        }

        // 4. 执行PDF转图片流程
        employeeFileVO.setRemarks(query.getUserNo());
        return processPdfToImages(employeeCode, employeeFileVO);
    }

    /**
     * 辅助方法：通过员工编号查询对应的PDF文件信息
     */
    private EmployeeFileVO getEmployeeFileByCode(String employeeCode) {
        EmployeeFileQuery fileQuery = new EmployeeFileQuery();
        fileQuery.setEmployeeCode(employeeCode);
        CommonPager<EmployeeFileVO> filePager = employeeFileService.listByPage(fileQuery);

        if (filePager == null || CollectionUtil.isEmpty(filePager.getDataList())) {
            log.warn("员工无关联的PDF文件，编号：{}", employeeCode);
            return null;
        }
        return filePager.getDataList().get(0);
    }

    /**
     * 核心流程：下载员工PDF -> 加水印 -> 转图片 -> 上传FTP -> 保存数据库
     */
    private List<EmployeeImagesVO> processPdfToImages(String employeeCode, EmployeeFileVO employeeFileVO) {
        List<EmployeeImagesVO> resultVOList = CollectionUtil.newArrayList();
        List<EmployeeImagesDO> newDOList = CollectionUtil.newArrayList();

        // 创建员工专属临时目录
        String localTempDir = TempFileManager.createBusinessTempDir(
                SystemConstant.TEMP_FILE_PATH,
                (long) employeeCode.hashCode()
        );

        // 定义临时文件路径
        String pdfFileName = employeeFileVO.getPdfFileUrl();
        String localOriginalPdfPath = localTempDir + "original_" + pdfFileName;
        String localWatermarkedPdfPath = localTempDir + "watermarked_" + pdfFileName;

        try {
            // 1. 从FTP下载原始PDF文件
            String ftpPdfPath = SystemConstant.FTP_EMPLOYEE_PATH + pdfFileName;
            log.info("开始下载员工PDF：FTP路径={}，本地路径={}", ftpPdfPath, localOriginalPdfPath);
            if (!ftpOperationUtil.downloadToLocal(ftpPdfPath, localOriginalPdfPath)) {
                String errorMsg = "员工PDF文件下载失败，编号：" + employeeCode;
                log.error(errorMsg);
                throw new ServiceException(errorMsg);
            }

            // 2. 给PDF添加水印
            SystemUserVO systemUserVO = systemUserService.getByUserNo(employeeFileVO.getRemarks());
            if (systemUserVO == null) {
                String errorMsg = "获取水印用户信息失败，用户编号：" + employeeFileVO.getRemarks();
                log.error(errorMsg);
                throw new ServiceException(errorMsg);
            }
            log.info("查看文件者:"+systemUserVO.getName());
            String watermarkText = "海外化工内部文件|禁止外传";

            log.info("开始添加水印：文本={}，原始PDF={}，目标PDF={}",
                    watermarkText, localOriginalPdfPath, localWatermarkedPdfPath);
            if (PdfUtil.addWatermark(localOriginalPdfPath, watermarkText, localWatermarkedPdfPath) == null) {
                String errorMsg = "PDF加水印失败，编号：" + employeeCode;
                log.error(errorMsg);
                throw new ServiceException(errorMsg);
            }

            // 3. 将带水印的PDF转换为图片
            log.info("开始转换PDF为图片：PDF路径={}，图片目录={}", localWatermarkedPdfPath, localTempDir);
            List<String> localImagePaths = PdfUtil.converter(localWatermarkedPdfPath, localTempDir);
            if (CollectionUtil.isEmpty(localImagePaths)) {
                String errorMsg = "PDF转图片失败，未生成任何图片，编号：" + employeeCode;
                log.error(errorMsg);
                throw new ServiceException(errorMsg);
            }

            // 4. 遍历图片，上传到FTP并构建数据库记录
            for (int i = 0; i < localImagePaths.size(); i++) {
                String localImgPath = localImagePaths.get(i);
                File localImgFile = new File(localImgPath);
                if (!localImgFile.exists()) {
                    log.warn("本地图片不存在，跳过：{}", localImgPath);
                    continue;
                }

                int pageNum = i + 1;
                String imgSuffix = FileUtil.getSuffix(localImgPath);
                String remoteImgName = String.format("%s_page_%d.%s", employeeCode, pageNum, imgSuffix);
                String remoteImgPath = SystemConstant.FTP_EMPLOYEE_IMAGES_PATH + remoteImgName;
                String imageUrl = String.format("%s:%d%s%s",
                        nginxHost, nginxPort, nginxEmployeeImagesPath, remoteImgName);

                log.info("上传图片到FTP：本地路径={}，FTP路径={}", localImgPath, remoteImgPath);
                if (!ftpOperationUtil.uploadFromLocal(localImgPath, remoteImgPath)) {
                    log.error("图片上传失败，跳过当前图片：{}", localImgPath);
                    continue;
                }

                EmployeeImagesDO imageDO = EmployeeImagesDO.builder()
                        .employeeCode(employeeCode)
                        .imageUrl(imageUrl)
                        .imagePath(remoteImgPath)
                        .pageNum(pageNum)
                        .imageFormat(imgSuffix)
                        .build();
                newDOList.add(imageDO);
            }

            // 5. 批量保存记录到数据库
            if (CollectionUtil.isNotEmpty(newDOList)) {
                newDOList.forEach(doItem -> this.insertSelective(BeanConvertUtil.convert(doItem, EmployeeImagesDTO.class)));
                resultVOList = BeanConvertUtil.convertList(newDOList, EmployeeImagesVO.class);
                log.info("员工PDF转图片完成，编号：{}，生成图片数量：{}", employeeCode, resultVOList.size());
            }

        } catch (Exception e) {
            log.error("员工PDF转图片异常，编号：{}", employeeCode, e);
            throw new ServiceException("员工文件转图片失败：" + e.getMessage());
        } finally {
            // 清理临时文件
            TempFileManager.cleanTempFiles(localOriginalPdfPath, localWatermarkedPdfPath, localTempDir);
            log.info("员工PDF处理临时文件清理完成，目录：{}", localTempDir);
        }

        return resultVOList;
    }
}