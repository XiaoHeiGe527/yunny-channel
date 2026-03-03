package com.yunny.channel.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.*;

import java.io.*;

/**
 * 专为Windows 10 FTP服务器（默认GBK编码）优化的工具类
 */
@Slf4j
public class SmartFtpUtil {
    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String remoteDirectory;

    public SmartFtpUtil(String host, int port, String username, String password, String remoteDirectory) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.remoteDirectory = remoteDirectory;
    }

    /**
     * 初始化FTP客户端，设置GBK编码
     */
    private FTPClient initFtpClient() throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(30000);
        ftpClient.setDataTimeout(60000);
        ftpClient.setControlEncoding("GBK"); // 关键设置：使用GBK编码

        // 连接并登录
        ftpClient.connect(host, port);
        int reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            throw new IOException("连接失败，响应码: " + reply);
        }

        if (!ftpClient.login(username, password)) {
            ftpClient.disconnect();
            throw new IOException("登录失败，用户名或密码错误");
        }

        // 启用被动模式
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        log.info("已连接到FTP服务器: {}:{}", host, port);

        return ftpClient;
    }

    /**
     * 确保目录存在（使用GBK编码处理路径）
     */
    private void ensureDirectoryExists(FTPClient ftpClient, String directory) throws IOException {
        if (directory == null || directory.isEmpty()) {
            return;
        }

        // 检查目录是否存在
        if (!ftpClient.changeWorkingDirectory(directory)) {
            // 目录不存在，尝试创建
            String[] dirs = directory.split("/");
            String currentDir = "";

            for (String dir : dirs) {
                if (dir.isEmpty()) {
                    continue;
                }

                currentDir += (currentDir.isEmpty() ? "" : "/") + dir;

                // 先尝试切换目录
                if (!ftpClient.changeWorkingDirectory(currentDir)) {
                    // 切换失败，尝试创建目录
                    if (!ftpClient.makeDirectory(currentDir)) {
                        // 创建失败，可能是权限问题或目录名非法
                        log.error("创建目录失败: {}", currentDir);

                        // 尝试使用原始字节数组创建目录（作为备选方案）
                        try {
                            byte[] dirBytes = currentDir.getBytes("GBK");
                            if (!ftpClient.makeDirectory(new String(dirBytes, "ISO-8859-1"))) {
                                throw new IOException("创建目录失败: " + currentDir);
                            }
                            log.info("成功创建目录: {}", currentDir);
                        } catch (Exception e) {
                            throw new IOException("创建目录失败: " + currentDir, e);
                        }
                    } else {
                        log.info("成功创建目录: {}", currentDir);
                    }

                    // 再次尝试切换到新创建的目录
                    if (!ftpClient.changeWorkingDirectory(currentDir)) {
                        throw new IOException("无法切换到目录: " + currentDir);
                    }
                }
            }
        }
    }

    /**
     * 上传文件（简化路径处理，使用预设目录）
     */
    public boolean uploadFile(InputStream inputStream, String fullRemotePath) {
        FTPClient ftpClient = null;
        try {
            ftpClient = initFtpClient();

            // 提取文件名（忽略路径，强制上传到预设目录）
            String fileName =fullRemotePath;
            log.info("==============[{}]",fileName);

            // 切换到预设目录（需确保该目录已存在）
            if (!ftpClient.changeWorkingDirectory(remoteDirectory)) {
                log.error("无法切换到预设目录: {}", remoteDirectory);
                return false;
            }

            // 上传文件
            boolean success = ftpClient.storeFile(fileName, inputStream);
            log.info("文件上传 {}，远程文件名: {}", success ? "成功" : "失败", fileName);
            return success;
        } catch (IOException e) {
            log.error("上传文件时发生异常", e);
            return false;
        } finally {
            disconnectFtpClient(ftpClient);
        }
    }

    /**
     * 下载文件（处理完整路径，不依赖构造函数的remoteDirectory）
     */
    public boolean downloadFile(String fullRemotePath, OutputStream outputStream) {
        FTPClient ftpClient = null;
        try {
            ftpClient = initFtpClient();

            // 提取目录和文件名
            String directory = extractDirectory(fullRemotePath);
            String fileName = extractFileName(fullRemotePath);

            // 确保目录存在（使用GBK编码）
            ensureDirectoryExists(ftpClient, directory);

            // 切换到下载目录
            if (!ftpClient.changeWorkingDirectory(directory)) {
                log.error("无法切换到目录: {}", directory);
                return false;
            }

            // 下载文件
            boolean success = ftpClient.retrieveFile(fileName, outputStream);
            log.info("文件下载 {}，远程路径: {}", success ? "成功" : "失败", fullRemotePath);
            return success;
        } catch (IOException e) {
            log.error("下载文件时发生异常", e);
            return false;
        } finally {
            disconnectFtpClient(ftpClient);
        }
    }

    /**
     * 从完整路径中提取目录部分
     */
    private String extractDirectory(String fullPath) {
        int lastSeparator = fullPath.lastIndexOf('/');
        return lastSeparator >= 0 ? fullPath.substring(0, lastSeparator) : "";
    }

    /**
     * 从完整路径中提取文件名部分
     */
    private String extractFileName(String fullPath) {
        int lastSeparator = fullPath.lastIndexOf('/');
        return lastSeparator >= 0 ? fullPath.substring(lastSeparator + 1) : fullPath;
    }

    /**
     * 断开FTP连接
     */
    private void disconnectFtpClient(FTPClient ftpClient) {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
                log.info("已断开FTP连接");
            } catch (IOException e) {
                log.error("断开连接时发生异常", e);
            }
        }
    }
}