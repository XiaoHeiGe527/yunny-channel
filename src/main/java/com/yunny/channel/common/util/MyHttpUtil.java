package com.yunny.channel.common.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class MyHttpUtil {


    /**
     * 文件下载输出
     *
     * @param fileName 下载的文件名（中文不要太多，最多支持17个中文，因为header有150个字节限制）
     * @param path     文件路径："/mydata/zijinchakong/一键分析报告.doc"
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public static void downLoad(@NotBlank String fileName, @NotBlank String path,
                                HttpServletResponse response) throws ServletException, IOException {
        // 创建输出流对象
        ServletOutputStream outputStream = response.getOutputStream();
        //以字节数组的形式读取文件
        byte[] bytes = FileUtil.readBytes(path);
        // 设置返回内容格式
        response.setContentType("application/octet-stream");
        // 把文件名按UTF-8取出并按ISO8859-1编码，保证弹出窗口中的文件名中文不乱码
        // 中文不要太多，最多支持17个中文，因为header有150个字节限制。
        // 这一步一定要在读取文件之后进行，否则文件名会乱码，找不到文件
        fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        // 设置下载弹窗的文件名和格式（文件名要包括名字和文件格式）
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        // 返回数据到输出流对象中
        outputStream.write(bytes);
        // 关闭流对象
        IoUtil.close(outputStream);

    }

    // File文件转换为base64编码
    public static String encodeBase64File(File file) throws Exception {
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);
    }

    // base64字符串转换为byte[]
    public static byte[] decodeBase64String(String base64Str) throws Exception {
        return Base64.getMimeDecoder().decode(base64Str);
    }


}
