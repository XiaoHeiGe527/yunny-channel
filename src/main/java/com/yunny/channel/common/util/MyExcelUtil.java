package com.yunny.channel.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.google.common.collect.Lists;

import com.yunny.channel.common.exception.MyAssert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MyExcelUtil {

    public static void export(Map<String, String> headerAlias, List dataList, String destFilePath, int sheetIndex, String sheetName) {
        log.info(">>>>>>>>   " + destFilePath + "   " + dataList.size());
        ExcelWriter writer = ExcelUtil.getBigWriter(destFilePath);
        if (CollUtil.isNotEmpty(headerAlias)) {
            //设置是否只保留别名中的字段值，如果为true，则不设置alias的字段将不被输出，false表示原样输出
            writer.setOnlyAlias(true);
            //自定义标题别名
            writer.setHeaderAlias(headerAlias);
        }

        if(CollUtil.isNotEmpty(dataList) && dataList.size()>10000000){
            List<List> lists = CollUtil.splitList(dataList, 500000);
            int index = sheetIndex;
            log.info(">>>>>>>>   " + destFilePath + "   " + sheetName+index);
            for (List list : lists) {
                writer.setSheet(index);
                writer.renameSheet(sheetName+index);
                writer.write(list, true);
                index++;
            }
        }else{
            writer.setSheet(sheetIndex);
            writer.renameSheet(sheetName);
            writer.write(dataList, true);
        }
        //关闭writer，释放内存
        writer.close();
    }

    public static void export(Map<String, String> headerAlias, List dataList, ExcelWriter writer, int sheetIndex, String sheetName) {
        if (CollUtil.isNotEmpty(headerAlias)) {
            //设置是否只保留别名中的字段值，如果为true，则不设置alias的字段将不被输出，false表示原样输出
            writer.setOnlyAlias(true);
            //自定义标题别名
            writer.setHeaderAlias(headerAlias);
        }
        writer.setSheet(sheetIndex);
        writer.renameSheet(sheetName);
        writer.write(dataList, true);
    }

    public static void exportBySize(Map<String, String> headerAlias, List dataList, ExcelWriter writer, int sheetIndex, String sheetName) {
        if (CollUtil.isNotEmpty(headerAlias)) {
            //设置是否只保留别名中的字段值，如果为true，则不设置alias的字段将不被输出，false表示原样输出
            writer.setOnlyAlias(true);
            //自定义标题别名
            writer.setHeaderAlias(headerAlias);
        }

        writer.setSheet(sheetIndex);
        writer.setCurrentRowToEnd();
        writer.write(dataList, false);
    }

    public static void exportResp(Map<String, String> headerAlias, List dataList,
                                  String destFilePath, int sheetIndex,
                                  String sheetName, HttpServletResponse response) throws Exception {
        export(headerAlias, dataList, destFilePath, sheetIndex, sheetName);
        String[] split = destFilePath.split("/");
        downloadFile(split[split.length - 1], destFilePath, response);
    }

    /**
     * 下载文件
     *
     * @param fileName
     * @param path
     * @param response
     * @throws Exception
     */
    public static void downloadFile(String fileName, String path,
                                    HttpServletResponse response) throws Exception {
        File file = new File(path);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename="
                + new String(fileName.getBytes("ISO8859-1"), StandardCharsets.UTF_8));
        response.setContentLength((int) file.length());
        response.setContentType("application/x-msdownload;charset=utf-8");
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream buff = new BufferedInputStream(fis);
        byte[] b = new byte[1024];// 相当于我们的缓存
        long k = 0;// 该值用于计算当前实际下载了多少字节
        OutputStream myout = response.getOutputStream();// 从response对象中得到输出流,准备下载
        // 开始循环下载
        while (k < file.length()) {
            int j = buff.read(b, 0, 1024);
            k += j;
            myout.write(b, 0, j);
        }
        myout.flush();
        buff.close();
    }


    /**
     * excel导入工具类
     *
     * @param file 文件
     * @return 返回数据集合
     * @throws Exception
     */
    public static List<Map<String, Object>> importFile(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        MyAssert.isTrue(StrUtil.isNotEmpty(fileName), "没有导入文件");
        MyAssert.isTrue(fileName.endsWith(".xls")
                || fileName.endsWith(".xlsx"), "文件格式不正确,请上传 .xlsx | .xls");

        List<List<Object>> lineList = new ArrayList<>();
        //读取数据
        ExcelUtil.readBySax(file.getInputStream(), 0, createRowHandler(lineList));
        return getExcelData(lineList);
    }

    /**
     * excel导入工具类
     *
     * @param file 文件
     * @return 返回数据集合
     * @throws Exception
     */
    public static List<Map<String, Object>> importFile(File file) throws Exception {
        String fileName = file.getName();
        MyAssert.isTrue(StrUtil.isNotEmpty(fileName), "没有导入文件");
        MyAssert.isTrue(fileName.endsWith(".xls")
                || fileName.endsWith(".xlsx"), "文件格式不正确,请上传 .xlsx | .xls");

        List<List<Object>> lineList = new ArrayList<>();
//        //读取数据
        InputStream input = new FileInputStream(file);
        ExcelUtil.readBySax(input, 0, createRowHandler(lineList));
        if (CollectionUtils.isNotEmpty(lineList)) {
            //如果不为空
            return getExcelData(lineList);
        }
        return Lists.newArrayList();
    }

    /**
     * excel导入工具类
     *
     * @param filePath 文件路径
     * @return 返回数据集合
     * @throws Exception
     */
    public static List<Map<String, Object>> importFile(String filePath) throws Exception {
        MyAssert.isTrue(filePath.endsWith(".xls")
                || filePath.endsWith(".xlsx"), "文件格式不正确,请上传 .xlsx | .xls");
        List<List<Object>> lineList = new ArrayList<>();
        //读取数据
        ExcelUtil.readBySax(filePath, 0, createRowHandler(lineList));
        return getExcelData(lineList);
    }

    /**
     * excel导入工具类--剔除前面removeBeginNum行,结尾removeEndNum行
     *
     * @param filePath         文件路径
     * @param removeBeginNum   移除开始行数  1为第一行
     * @param removeEndNum     移除结尾行数
     * @param columNamesrowNum 标题所在行  0为第一行
     * @return 返回数据集合
     * @throws Exception
     */
    public static List<Map<String, Object>> importFileByCutStartEndRow(String filePath, int removeBeginNum, int removeEndNum, int columNamesrowNum) throws Exception {
        MyAssert.isTrue(filePath.endsWith(".xls")
                || filePath.endsWith(".xlsx"), "文件格式不正确,请上传 .xlsx | .xls");
        List<List<Object>> lineList = new ArrayList<>();
        //读取数据
        ExcelUtil.readBySax(filePath, 0, createRowHandler(lineList));
        return getExcelDataRemoveNrow(lineList, removeBeginNum, removeEndNum, columNamesrowNum);
    }

    /**
     * excel导入工具类--剔除前面removeBeginNum行,结尾removeEndNum行
     *
     * @param filePath         文件路径
     * @param removeBeginNum   移除开始行数  1为第一行
     * @param removeEndNum     移除结尾行数
     * @param columNamesrowNum 标题所在行  0为第一行
     * @param readSheet        读取sheet -1为所有，0为第一个
     * @return 返回数据集合
     * @throws Exception
     */
    public static List<Map<String, Object>> importFileByCutStartEndRow(String filePath, int removeBeginNum, int removeEndNum, int columNamesrowNum, int readSheet) throws Exception {
        MyAssert.isTrue(filePath.endsWith(".xls")
                || filePath.endsWith(".xlsx"), "文件格式不正确,请上传 .xlsx | .xls");
        List<List<Object>> lineList = new ArrayList<>();
        //读取数据
        ExcelUtil.readBySax(filePath, readSheet, createRowHandler(lineList));
        return getExcelDataRemoveNrow(lineList, removeBeginNum, removeEndNum, columNamesrowNum);
    }


    /**
     * 获取Excel数据
     *
     * @param lineList
     * @return
     */
    private static List<Map<String, Object>> getExcelData(List<List<Object>> lineList) {
        //去除excel中的第一行数据
        MyAssert.isTrue(lineList.size() > 1, "文件没有数据");

        List<Object> columNames = lineList.get(0);
        lineList.remove(0);

        //将数据封装到list<Map>中
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (int i = 0; i < lineList.size(); i++) {
            if (null != lineList.get(i)) {
                Map<String, Object> hashMap = new HashMap<>();
                for (int j = 0; j < columNames.size(); j++) {
                    Object property = lineList.get(i).get(j);
                    String columName = String.valueOf(columNames.get(j));
                    hashMap.put(columName, property);
                }
                dataList.add(hashMap);
            }
        }
        return dataList;
    }


    /**
     * 获取Excel数据
     * 剔除开头前N行数据，剔除结尾N行数据
     *
     * @param lineList
     * @param removeBeginNum
     * @param columNamesrowNum
     * @return
     */
    private static List<Map<String, Object>> getExcelDataRemoveNrow(List<List<Object>> lineList, int removeBeginNum, int removeEndNum, int columNamesrowNum) {
        //去除excel中的第一行数据
        MyAssert.isTrue(lineList.size() > 1, "文件没有数据");
        List<Object> columNames = lineList.get(columNamesrowNum);

        for (int i = 0; i < removeBeginNum; i++) {
            lineList.remove(0);
        }

        for (int i = 0; i < removeEndNum; i++) {
            lineList.remove(lineList.size() - 1);
        }

        //将数据封装到list<Map>中
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (int i = 0; i < lineList.size(); i++) {
            if (null != lineList.get(i)) {
                Map<String, Object> hashMap = new HashMap<>();
                for (int j = 0; j < columNames.size(); j++) {
                    Object property = lineList.get(i).get(j);
                    String columName = String.valueOf(columNames.get(j));
                    hashMap.put(columName, property);
                }
                dataList.add(hashMap);
            }
        }
        return dataList;
    }

    /**
     * 通过实现handle方法编写我们要对每行数据的操作方式
     */
    private static RowHandler createRowHandler(List<List<Object>> lineList) {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowList) {
                //将读取到的每一行数据放入到list集合中
                //System.out.println("sheetIndex:" + sheetIndex);
                //System.out.println("rowIndex:" + rowIndex);
                //System.out.println("rowList.size():" + rowList.size());
                JSONArray jsonObject = new JSONArray(rowList);
                lineList.add(jsonObject.toList(Object.class));
            }
        };
    }

}
