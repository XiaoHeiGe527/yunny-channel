package com.yunny.channel.common.util;


import com.yunny.channel.config.FreeMarkerConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

public class MyWordUtil {


    /**
     * 生成word文件
     * @param templateName word模板名称，例如：test.ftl
     * @param dataMap      word中需要展示的动态数据，用map集合来保存
     * @param filePath     文件生成的目标路径，例如：D:/wordFile/
     * @param fileName     生成的文件名称，例如：test.doc
     */
    public static void createWord(FreeMarkerConfig freeMarkerConfig, String templateName,
                                  Map<String, Object> dataMap, String filePath, String fileName) throws Exception {
        // 获取模板
        Configuration configuration = freeMarkerConfig
                .getConfiguration("/templates");
        Template template = configuration.getTemplate(templateName);
        // 输出文件
        File outFile = new File(filePath + fileName);
        // 如果输出目标文件夹不存在，则创建
        if (!outFile.getParentFile().exists()) {
            outFile.getParentFile().mkdirs();
        }
        Writer out = null;
        try {
            // 将模板和数据模型合并生成文件
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(outFile), "UTF-8"));
            // 生成文件
            template.process(dataMap, out);
            out.flush();
        } catch (Exception e) {
            throw e;
        } finally {
            // 关闭流
            if (out != null)
                out.close();
        }

    }


}
