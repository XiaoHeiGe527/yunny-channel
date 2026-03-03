package com.yunny.channel.service.ftp.impl;

import com.yunny.channel.common.constant.SystemConstant;
import com.yunny.channel.service.ftp.FtpService;
import com.yunny.channel.util.SmartFtpUtil; // 引入新工具类
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * FTP相关接口类 - 已适配Windows 10 FTP服务器（GBK编码）
 */
@Slf4j
@Service
public class FtpServiceImpl implements FtpService {

    @Value("${ftp.server.host}")
    private String host;

    @Value("${ftp.server.port}")
    private int port;

    @Value("${ftp.server.username}")
    private String username;

    @Value("${ftp.server.password}")
    private String password;

    @Value("${ftp.server.remote-directory}")
    private String remoteDirectory; // 注意：此参数不再用于指定上传目录，仅作为路径前缀

    /**
     * 上传文件到FTP服务器
     */
    @Override
    public boolean uploadFileToFtp(MultipartFile file, String remoteFileName) {
        log.info("准备上传文件: {}", remoteFileName);

        try (InputStream inputStream = file.getInputStream()) {
            // 组合完整路径（使用系统常量中的目录 + 文件名）
            // String fullRemotePath = SystemConstant.FTP_EMPLOYEE_PATH + remoteFileName;

            //log.info("fullRemotePath=========[{}]",fullRemotePath);

            // 创建新工具类实例（注意：remoteDirectory参数留空，由完整路径处理）
            SmartFtpUtil ftpUtil = new SmartFtpUtil(host, port, username, password, remoteDirectory);

            // 上传文件（使用完整路径）
            boolean success = ftpUtil.uploadFile(inputStream, remoteFileName);

            if (success) {
                log.info("文件上传成功: {}", remoteFileName);
                return true;
            } else {
                log.error("文件上传失败: {}", remoteFileName);
                return false;
            }
        } catch (IOException e) {
            log.error("上传文件时发生异常", e);
            return false;
        }
    }

    /**
     * 处理文件上传请求
     */
    @Override
    public String handleFileUpload(MultipartFile multipartFile, String fileLabel) {
        if (multipartFile.isEmpty()) {
            return "请选择要上传的文件";
        }

        // 生成唯一文件名
        String originalFilename = multipartFile.getOriginalFilename();
        String fileExtension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";

        String uniqueFileName = fileLabel + "_" + System.currentTimeMillis() + fileExtension;

        // 调用上传方法
        boolean uploadSuccess = uploadFileToFtp(multipartFile, uniqueFileName);

        return uploadSuccess
                ? "文件上传成功，远程文件名: " + uniqueFileName
                : "文件上传失败，请稍后重试";
    }

    /**
     * 从FTP服务器下载文件
     */
    @Override
    public boolean downloadFile(String remoteFileName, HttpServletResponse response,String path) {
        try {
            // 组合完整路径
            String fullRemotePath = path + remoteFileName;

            // 设置响应头（使用GBK编码处理文件名）
            String encodedFileName = URLEncoder.encode(remoteFileName, "GBK");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + encodedFileName + "\"");

            try (OutputStream outputStream = response.getOutputStream()) {
                // 创建新工具类实例（注意：remoteDirectory参数留空）
                SmartFtpUtil ftpUtil = new SmartFtpUtil(host, port, username, password, remoteDirectory);

                // 下载文件（使用完整路径）
                return ftpUtil.downloadFile(fullRemotePath, outputStream);
            }
        } catch (IOException e) {
            log.error("下载文件时发生异常", e);
            return false;
        }
    }
}