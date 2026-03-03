package com.yunny.channel.util;

/**
 * @ClassName FtpUploadUtil
 * @Description TODO
 * @Author sunfuwei521@qq.com
 * @Date 2025/5/19 16:56
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * FTP上传工具类，提供可靠的文件上传功能
 */
@Slf4j
public class FtpUploadUtil {

    private String host;
    private int port;
    private String username;
    private String password;
    private String remoteDirectory;
    private int connectTimeout = 30000; // 默认连接超时时间30秒
    private int dataTimeout = 60000; // 默认数据传输超时时间60秒

    public FtpUploadUtil(String host, int port, String username, String password, String remoteDirectory) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.remoteDirectory = remoteDirectory;
    }

    /**
     * 设置连接超时时间
     * @param timeout 超时时间（毫秒）
     */
    public void setConnectTimeout(int timeout) {
        this.connectTimeout = timeout;
    }

    /**
     * 设置数据传输超时时间
     * @param timeout 超时时间（毫秒）
     */
    public void setDataTimeout(int timeout) {
        this.dataTimeout = timeout;
    }

    /**
     * 上传 MultipartFile 到 FTP 服务器
     * @param file 要上传的文件
     * @param remoteFileName 远程文件名
     * @return 上传结果，包含成功或失败信息
     */
    public FtpUploadResult uploadMultipartFile(MultipartFile file, String remoteFileName) {
        FTPClient ftpClient = new FTPClient();
        try {
            // 初始化并连接到 FTP 服务器
            initFtpClient(ftpClient);

            // 检查并创建远程目录
            ensureDirectoryExists(ftpClient, remoteDirectory);

            // 切换到上传目录
            if (!ftpClient.changeWorkingDirectory(remoteDirectory)) {
                String errorMsg = "无法切换到目标目录: " + remoteDirectory;
                log.error(errorMsg);
                return FtpUploadResult.failure(errorMsg);
            }

            // 设置文件类型为二进制
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // 上传文件
            try (InputStream inputStream = file.getInputStream()) {
                // 使用 UTF-8 编码处理文件名，防止中文乱码
                String encodedFileName = new String(remoteFileName.getBytes(StandardCharsets.UTF_8),
                        StandardCharsets.UTF_8);

                log.info("开始上传文件: {}, 远程文件名: {}", file.getOriginalFilename(), encodedFileName);

                boolean success = ftpClient.storeFile(encodedFileName, inputStream);
                if (success) {
                    log.info("文件上传成功: {}", file.getOriginalFilename());
                    return FtpUploadResult.success();
                } else {
                    // 获取 FTP 服务器的响应代码和消息
                    int replyCode = ftpClient.getReplyCode();
                    String replyMsg = ftpClient.getReplyString();
                    String errorMsg = "文件上传失败，响应码: " + replyCode + ", 响应信息: " + replyMsg;
                    log.error(errorMsg);
                    return FtpUploadResult.failure(errorMsg);
                }
            }
        } catch (IOException e) {
            // 详细记录异常信息
            String errorMsg = "FTP上传过程中发生IO异常: " + e.getMessage();
            log.error(errorMsg, e);
            return FtpUploadResult.failure(errorMsg);
        } catch (Exception e) {
            // 捕获其他未知异常
            String errorMsg = "FTP上传过程中发生未知异常: " + e.getMessage();
            log.error(errorMsg, e);
            return FtpUploadResult.failure(errorMsg);
        } finally {
            // 确保断开 FTP 连接
            disconnectFtpClient(ftpClient);
        }
    }




    // 在 FtpUploadUtil 类中添加以下方法
    public boolean downloadFile(String remoteFileName, OutputStream outputStream) {
        FTPClient ftpClient = new FTPClient();
        try {
            // 复用工具类已有的初始化逻辑
            initFtpClient(ftpClient);

            // 切换到目标目录（复用目录检查逻辑）
            if (!ftpClient.changeWorkingDirectory(remoteDirectory)) {
                String errorMsg = "无法切换到目标目录: " + remoteDirectory;
                log.error(errorMsg);
                return false;
            }

            // 设置二进制传输模式
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // 处理中文文件名（使用UTF-8编码）
            String encodedFileName = new String(remoteFileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);

            // 下载文件
            boolean success = ftpClient.retrieveFile(encodedFileName, outputStream);
            if (success) {
                log.info("文件下载成功: {}", remoteFileName);
                return true;
            } else {
                int replyCode = ftpClient.getReplyCode();
                String replyMsg = ftpClient.getReplyString();
                String errorMsg = "文件下载失败，响应码: " + replyCode + ", 响应信息: " + replyMsg;
                log.error(errorMsg);
                return false;
            }
        } catch (IOException e) {
            log.error("FTP下载过程中发生IO异常: " + e.getMessage(), e);
            return false;
        } finally {
            // 复用工具类已有的断开连接逻辑
            disconnectFtpClient(ftpClient);
        }
    }


    /**
     * 下载文件到本地 OutputStream
     */
    public boolean downloadFileToLocal(String remoteFileName, OutputStream outputStream) {
        FTPClient ftpClient = new FTPClient();
        try {
            initFtpClient(ftpClient);
            if (!ftpClient.changeWorkingDirectory(remoteDirectory)) {
                log.error("无法切换到目标目录: {}", remoteDirectory);
                return false;
            }
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            String encodedFileName = new String(
                    remoteFileName.getBytes(StandardCharsets.UTF_8),
                    StandardCharsets.UTF_8
            );

            log.info("最终传递的远程文件名：{}", encodedFileName);

            boolean success = ftpClient.retrieveFile(encodedFileName, outputStream);
            if (success) log.info("文件下载到本地成功: {}", remoteFileName);
            return success;
        } catch (IOException e) {
            log.error("FTP下载到本地失败: {}", e.getMessage(), e);
            return false;
        } finally {
            disconnectFtpClient(ftpClient);
        }
    }



    /**
     * 初始化 FTP 客户端并连接到服务器
     */
    private void initFtpClient(FTPClient ftpClient) throws IOException {
        // 设置连接参数
        ftpClient.setConnectTimeout(connectTimeout);
        ftpClient.setDataTimeout(dataTimeout);
        ftpClient.setControlEncoding("UTF-8");

        // 连接到服务器
        log.info("连接到 FTP 服务器: {}:{}", host, port);
        ftpClient.connect(host, port);

        // 检查连接响应
        int reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            String errorMsg = "FTP服务器拒绝连接，响应码: " + reply;
            log.error(errorMsg);
            throw new IOException(errorMsg);
        }

        // 登录
        log.info("使用用户名 {} 登录 FTP 服务器", username);
        if (!ftpClient.login(username, password)) {
            ftpClient.disconnect();
            String errorMsg = "FTP登录失败，用户名或密码错误";
            log.error(errorMsg);
            throw new IOException(errorMsg);
        }

        // 启用被动模式
        ftpClient.enterLocalPassiveMode();
        log.info("已启用 FTP 被动模式");
    }

    /**
     * 确保远程目录存在，如果不存在则创建
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

                currentDir += "/" + dir;
                if (!ftpClient.changeWorkingDirectory(currentDir)) {
                    if (!ftpClient.makeDirectory(currentDir)) {
                        String errorMsg = "创建目录失败: " + currentDir;
                        log.error(errorMsg);
                        throw new IOException(errorMsg);
                    }
                    log.info("成功创建目录: {}", currentDir);

                    // 创建目录后切换到该目录
                    if (!ftpClient.changeWorkingDirectory(currentDir)) {
                        String errorMsg = "切换到新创建的目录失败: " + currentDir;
                        log.error(errorMsg);
                        throw new IOException(errorMsg);
                    }
                }
            }
        }
    }

    /**
     * 安全断开 FTP 连接
     */
    private void disconnectFtpClient(FTPClient ftpClient) {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                if (!ftpClient.logout()) {
                    log.warn("FTP登出失败");
                }
                ftpClient.disconnect();
                log.info("已断开 FTP 连接");
            } catch (IOException e) {
                log.error("断开 FTP 连接时发生异常", e);
            }
        }
    }

    /**
     * FTP 上传结果类
     */
    public static class FtpUploadResult {
        private boolean success;
        private String message;

        private FtpUploadResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public static FtpUploadResult success() {
            return new FtpUploadResult(true, "上传成功");
        }

        public static FtpUploadResult failure(String message) {
            return new FtpUploadResult(false, message);
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "FtpUploadResult{" +
                    "success=" + success +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}