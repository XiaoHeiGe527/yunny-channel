package com.yunny.channel.common.util;

import cn.hutool.core.io.file.FileWriter;
import org.apache.poi.ss.formula.functions.T;

public class MyFileUtil {

    /**
     * 在目标目录下生成文件
     * @param fileName 带路径的文件名
     * @param content 文件里的内容
     */
    public static void createFile(String fileName, String content) {
        FileWriter fw = new FileWriter(fileName);
        fw.write(content);
    }


    /**
     *
     * @param fileName
     * @param clazz
     */
    public static void ParsingFile(String fileName, T clazz){

    }
}
