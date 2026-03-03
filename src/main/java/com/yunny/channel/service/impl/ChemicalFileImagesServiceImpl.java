package com.yunny.channel.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import com.yunny.channel.common.constant.SystemConstant;
import com.yunny.channel.common.entity.ChemicalFileImagesDO;

import com.yunny.channel.common.dto.ChemicalFileImagesDTO;
import com.yunny.channel.common.util.PdfUtil;
import com.yunny.channel.common.vo.ChemicalFileImagesVO;
import com.yunny.channel.common.query.ChemicalFileImagesQuery;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;

import com.yunny.channel.common.vo.ChemicalFileVO;
import com.yunny.channel.common.vo.SystemUserVO;
import com.yunny.channel.mapper.ChemicalFileImagesMapper;
import com.yunny.channel.service.ChemicalFileImagesService;
import com.yunny.channel.service.ChemicalFileService;
import com.yunny.channel.service.SystemUserService;
import com.yunny.channel.util.BeanConvertUtil;
import com.yunny.channel.util.FtpOperationUtil;
import com.yunny.channel.util.SmartFtpUtil;
import com.yunny.channel.util.TempFileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import java.util.stream.Collectors;


/**
 * Created by Fe
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class ChemicalFileImagesServiceImpl implements ChemicalFileImagesService {

    @Autowired
    private ChemicalFileService chemicalFileService;
    @Resource
    private ChemicalFileImagesMapper chemicalFileImagesMapper;
    @Autowired
    private FtpOperationUtil ftpOperationUtil; // 注入FTP工具类

    @Autowired
    private SystemUserService systemUserService;

    @Value("${nginx.server.host}")
    private String nginxHost;
    @Value("${nginx.server.port}")
    private int nginxPort;
    @Value("${nginx.server.ChemicalFileImages}")
    private String nginxImages;


    @Override
    public CommonPager<ChemicalFileImagesVO> listByPage(ChemicalFileImagesQuery query) {
        PageParameter page = query.getPageParameter();
        return new CommonPager<>(
                new PageParameter(page.getCurrentPage(), page.getPageSize(), chemicalFileImagesMapper.countByQuery(query)),
                BeanConvertUtil.convertList(chemicalFileImagesMapper.listByQuery(query), ChemicalFileImagesVO.class)
        );
    }

    @Override
    public ChemicalFileImagesVO getById(Long id) {
        return BeanConvertUtil.convert(chemicalFileImagesMapper.getById(id), ChemicalFileImagesVO.class);
    }

    @Override
    public int insertSelective(ChemicalFileImagesDTO dto) {
        return chemicalFileImagesMapper.insertSelective(BeanConvertUtil.convert(dto, ChemicalFileImagesDO.class));
    }

    @Override
    public int updateSelective(ChemicalFileImagesDTO dto) {
        return chemicalFileImagesMapper.updateSelective(BeanConvertUtil.convert(dto, ChemicalFileImagesDO.class));
    }

    @Override
    public List<ChemicalFileImagesVO> listByChemicalFileId(ChemicalFileImagesQuery query) {
        Long chemicalFileId = query.getChemicalFileId();
        // 1. 校验PDF是否存在
        ChemicalFileVO chemicalFileVO = chemicalFileService.getById(chemicalFileId);
        if (chemicalFileVO == null) {
            log.warn("PDF文件不存在，ID：{}", chemicalFileId);
            return null;
        }

        // 2. 查询已有图片记录
        List<ChemicalFileImagesDO> existingDOs = chemicalFileImagesMapper.listByQuery(query);
        if (CollectionUtil.isNotEmpty(existingDOs)) {
            log.info("已存在图片记录，ID：{}，数量：{}", chemicalFileId, existingDOs.size());
            return BeanConvertUtil.convertList(existingDOs, ChemicalFileImagesVO.class);
        }

        chemicalFileVO.setRemarks(query.getUserNo());

        // 3. 处理PDF转图片
        return processPdfToImages(chemicalFileId, chemicalFileVO);
    }


    /**
     * 核心流程：先给PDF加水印，再转换为图片并存储
     */
    private List<ChemicalFileImagesVO> processPdfToImages(Long chemicalFileId, ChemicalFileVO chemicalFileVO) {
        List<ChemicalFileImagesVO> resultVOList = CollectionUtil.newArrayList();
        List<ChemicalFileImagesDO> newDOList = CollectionUtil.newArrayList();
        // 创建临时目录（按文件ID隔离，避免冲突）
        String localTempDir = TempFileManager.createBusinessTempDir(SystemConstant.TEMP_FILE_PATH, chemicalFileId);

        // 定义临时文件路径（原始PDF、带水印PDF）
        String localOriginalPdfPath = localTempDir + "original_" + chemicalFileVO.getPdfFileUrl(); // 原始PDF
        String localWatermarkedPdfPath = localTempDir + "watermarked_" + chemicalFileVO.getPdfFileUrl(); // 带水印PDF

        try {
            // 1. 从FTP下载原始PDF到本地
            String ftpPdfPath = SystemConstant.FTP_CHEMICAL_PATH + chemicalFileVO.getPdfFileUrl();
            log.info("从FTP下载原始PDF：{} -> 本地：{}", ftpPdfPath, localOriginalPdfPath);
            if (!ftpOperationUtil.downloadToLocal(ftpPdfPath, localOriginalPdfPath)) {
                log.error("原始PDF下载失败，终止流程");
                return null;
            }

            // 2. 给PDF添加水印（关键步骤：使用现有PdfUtil工具类）
            log.info("给PDF添加水印：{} -> 带水印：{}", localOriginalPdfPath, localWatermarkedPdfPath);
            SystemUserVO systemUserVO = systemUserService.getByUserNo(chemicalFileVO.getRemarks());
            log.info("查看文件者:"+systemUserVO.getName());

            String watermarkText = "海外化工内部文件|禁止外传";
            if (PdfUtil.addWatermark(localOriginalPdfPath, watermarkText, localWatermarkedPdfPath) == null) {
                log.error("PDF加水印失败，终止流程");
                return null;
            }
            // 3. 将带水印的PDF转换为图片
            log.info("转换带水印的PDF为图片，路径：{}", localWatermarkedPdfPath);
            List<String> localImagePaths = PdfUtil.converter(localWatermarkedPdfPath, localTempDir);
            if (CollectionUtil.isEmpty(localImagePaths)) {
                log.error("带水印PDF转图片失败，终止流程");
                return null;
            }

            // 4. 上传图片到FTP并构建数据库记录（与原逻辑一致）
            for (int i = 0; i < localImagePaths.size(); i++) {
                String localImgPath = localImagePaths.get(i);
                int pageNum = i + 1;
                String imgSuffix = FileUtil.getSuffix(localImgPath);
                String remoteImgName = chemicalFileId + "_page_" + pageNum + "." + imgSuffix;
                String remoteImgPath = SystemConstant.PDF_IMAGES_PATH + remoteImgName;
                String imageUrl = nginxHost + ":" + nginxPort + nginxImages + remoteImgName;

                // 上传图片到FTP
                if (!ftpOperationUtil.uploadFromLocal(localImgPath, remoteImgPath)) {
                    log.error("图片上传失败，跳过：{}", localImgPath);
                    continue;
                }

                // 构建数据库记录
                newDOList.add(ChemicalFileImagesDO.builder()
                        .chemicalFileId(chemicalFileId)
                        .imageUrl(imageUrl)
                        .imagePath(remoteImgPath)
                        .pageNum(pageNum)
                        .imageFormat(imgSuffix)
                        .build());
            }

            // 5. 保存数据库并返回结果
            if (CollectionUtil.isNotEmpty(newDOList)) {
                newDOList.forEach(doItem -> this.insertSelective(BeanConvertUtil.convert(doItem, ChemicalFileImagesDTO.class)));
                resultVOList = BeanConvertUtil.convertList(newDOList, ChemicalFileImagesVO.class);
                log.info("带水印PDF转图片完成，数量：{}", resultVOList.size());
            }
            return resultVOList;

        } catch (Exception e) {
            log.error("带水印PDF转图片异常，ID：{}", chemicalFileId, e);
            throw new RuntimeException("带水印PDF转图片失败", e);
        } finally {
            // 清理所有临时文件（原始PDF、带水印PDF、图片、临时目录）
            TempFileManager.cleanTempFiles(localOriginalPdfPath, localWatermarkedPdfPath, localTempDir);
        }
    }

}
