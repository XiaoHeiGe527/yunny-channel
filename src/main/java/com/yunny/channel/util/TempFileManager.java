package com.yunny.channel.util;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * 临时文件管理工具类（TempFileManager）
 * 封装临时目录创建、清理逻辑：
 * @ClassName TempFileManager
 * @Description TODO
 * @Author sunfuwei521@qq.com
 * @Date 2025/11/4 14:07
 */
@Slf4j
public class TempFileManager {
    /**
     * 创建按业务ID隔离的临时目录
     * @param baseTempDir 基础临时目录（如配置中的tempDir）
     * @param businessId 业务ID（如chemicalFileId）
     * @return 临时目录路径
     */
    public static String createBusinessTempDir(String baseTempDir, Long businessId) {
        String tempDir = baseTempDir + File.separator + "pdf_" + businessId + File.separator;
        FileUtil.mkdir(tempDir);
        return tempDir;
    }

    /**
     * 清理临时文件/目录
     * @param paths 要清理的路径列表
     */
    public static void cleanTempFiles(String... paths) {
        for (String path : paths) {
            if (FileUtil.exist(path)) {
                boolean success = FileUtil.del(path);
                log.info("清理临时文件：{}，结果：{}", path, success ? "成功" : "失败");
            }
        }
    }
}