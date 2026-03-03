package com.yunny.channel.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

import java.io.FileOutputStream;

/**
 *
 * FTP 操作工具类（FtpOperationUtil）
 * 封装重复的 FTP 上传 / 下载逻辑，替代直接使用SmartFtpUtil：
 * @ClassName FtpOperationUtil
 * @Description TODO
 * @Author sunfuwei521@qq.com
 * @Date 2025/11/4 14:04
 */
@Slf4j
@Component
public class FtpOperationUtil {
    @Value("${ftp.server.host}")
    private String ftpHost;
    @Value("${ftp.server.port}")
    private int ftpPort;
    @Value("${ftp.server.username}")
    private String ftpUsername;
    @Value("${ftp.server.password}")
    private String ftpPassword;

    /**
     * 从FTP下载文件到本地
     * @param remotePath FTP远程路径
     * @param localFilePath 本地文件路径
     * @return 是否成功
     */
    public boolean downloadToLocal(String remotePath, String localFilePath) {
        try (FileOutputStream fos = new FileOutputStream(localFilePath)) {
            SmartFtpUtil ftpUtil = new SmartFtpUtil(ftpHost, ftpPort, ftpUsername, ftpPassword, "");
            return ftpUtil.downloadFile(remotePath, fos);
        } catch (Exception e) {
            log.error("FTP下载失败，远程路径：{}，本地路径：{}", remotePath, localFilePath, e);
            return false;
        }
    }

    /**
     * 上传本地文件到FTP
     * @param localFilePath 本地文件路径
     * @param remotePath FTP远程路径
     * @return 是否成功
     */
    public boolean uploadFromLocal(String localFilePath, String remotePath) {
        File localFile = new File(localFilePath);
        if (!localFile.exists()) {
            log.warn("本地文件不存在：{}", localFilePath);
            return false;
        }
        try (FileInputStream fis = new FileInputStream(localFile)) {
            SmartFtpUtil ftpUtil = new SmartFtpUtil(ftpHost, ftpPort, ftpUsername, ftpPassword, "");
            return ftpUtil.uploadFile(fis, remotePath);
        } catch (Exception e) {
            log.error("FTP上传失败，本地路径：{}，远程路径：{}", localFilePath, remotePath, e);
            return false;
        }
    }

    /**
     * 直接通过流上传到FTP
     * @param inputStream 输入流
     * @param remotePath FTP远程路径
     * @return 是否成功
     */
    public boolean uploadFromStream(InputStream inputStream, String remotePath) {
        try {
            SmartFtpUtil ftpUtil = new SmartFtpUtil(ftpHost, ftpPort, ftpUsername, ftpPassword, "");
            return ftpUtil.uploadFile(inputStream, remotePath);
        } catch (Exception e) {
            log.error("FTP流上传失败，远程路径：{}", remotePath, e);
            return false;
        }
    }
}