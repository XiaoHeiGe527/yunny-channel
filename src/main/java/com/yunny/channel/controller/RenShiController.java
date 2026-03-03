package com.yunny.channel.controller;

import com.yunny.channel.common.query.EmployeeFileQuery;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.tools.aspect.lock.RedisLock;
import com.yunny.channel.service.EmployeeFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.net.URLEncoder;

import static com.yunny.channel.common.constant.SystemConstant.TEMP_FILE_PATH;
import static org.apache.poi.hssf.record.FormulaSpecialCachedValue.ERROR_CODE;

/**
 * @ClassName RenShiController
 * @Description TODO
 * @Author sunfuwei521@qq.com
 * @Date 2025/5/14 16:02
 */
@Validated
@RestController
@RequestMapping("/renShi")
@Slf4j
public class RenShiController {


    @Autowired
    private EmployeeFileService employeeFileService;

    // 本地临时目录（需提前创建）
    @Value("${pdf.temp-dir:/temp/pdf-watermark/}")
    private String tempDir;

    //TODO 加日志！


    /**
     * 员工档案录入（PDF文件上传FTP）
     *
     * @param file
     * @param employeeCode
     * @param name
     * @param department
     * @param rank
     * @param profession
     * @param positionCode
     * @param state
     * @param remarks
     * @param pdfFileUrl
     * @return
     */
    @RedisLock
    @PostMapping("/handleFileUpload")
    public BaseResult handleFileUpload(MultipartFile file,
                                       @RequestParam("employeeCode") String employeeCode,
                                       @RequestParam("name") String name,
                                       @RequestParam("department") String department,
                                       @RequestParam("rank") String rank,
                                       @RequestParam("profession") String profession,
                                       @RequestParam("positionCode") String positionCode,
                                       @RequestParam("state") Integer state,
                                       @RequestParam("remarks") String remarks,
                                       @RequestParam("pdfFileUrl") String pdfFileUrl) {
        // 文件为空校验
        if (file == null || file.isEmpty()) {
            return BaseResult.failure(HttpURLConnection.HTTP_SERVER_ERROR, "请上传文件");
        }

        // 文件类型校验
        String originalFilename = file.getOriginalFilename();
        if (!originalFilename.endsWith(".pdf") && !originalFilename.endsWith(".PDF")) {
            return BaseResult.failure(HttpURLConnection.HTTP_SERVER_ERROR, "仅支持PDF文件上传");
        }

        // 文件大小校验（1GB限制）
        long fileSize = file.getSize();
        long maxSize = 1024 * 1024 * 1024; // 1GB
        if (fileSize > maxSize) {
            return BaseResult.failure(HttpURLConnection.HTTP_SERVER_ERROR, "文件大小不能超过1GB");
        }

        // 业务处理
        return employeeFileService.handleFileUpload(file, EmployeeFileQuery.builder()
                .employeeCode(employeeCode)
                .name(name)
                .department(department)
                .rank(rank)
                .profession(profession)
                .positionCode(positionCode)
                .state(state)
                .remarks(remarks)
                .pdfFileUrl(pdfFileUrl)
                .build());
    }

    /**
     * 添加水印并下载接口  @RedisLock 分布式锁 锁方法，后期加令牌同也行
     */
    @RedisLock
    @PostMapping("/addWatermarkAndDownload")
    public void addWatermarkAndDownload(
            @RequestParam("remoteFileName") String remoteFileName,
            @RequestParam(value = "watermarkText", defaultValue = "海外民爆档案室私密文件禁止外传") String watermarkText,
            HttpServletResponse response
    ) {
        try {
            // 设置响应头
            String encodedFileName = URLEncoder.encode(
                    "watermark_" + remoteFileName,
                    "UTF-8"
            );
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + encodedFileName + "\""
            );

            // 调用服务层处理水印并输出流
            employeeFileService.addWatermarkAndDownload(
                    remoteFileName,
                    watermarkText,
                    response.getOutputStream()
            );

        } catch (IOException e) {
            log.error("响应设置失败: {}", e.getMessage(), e);
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "操作失败");
            } catch (IOException ex) {
                log.error("错误响应失败", ex);
            }
        }
    }

    /**
     * 下载文件
     *
     * @param remoteFileName 远程文件名
     * @param response       HttpServletResponse
     */
    @RedisLock
    @PostMapping("/downloadFile")
    public void downloadFile(
            @RequestParam(value = "remoteFileName") String remoteFileName,
            HttpServletResponse response
    ) {
        try {
            // 设置响应头
            response.setContentType("application/pdf");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + java.net.URLEncoder.encode(remoteFileName, "UTF-8") + "\"");

            // 调用服务层方法直接写入响应流
            employeeFileService.downloadFile(remoteFileName, response);

        } catch (IOException e) {
            log.error("下载文件失败: {}", e.getMessage(), e);
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "下载文件失败");
            } catch (IOException ex) {
                log.error("设置错误响应失败", ex);
            }
        }
    }

}