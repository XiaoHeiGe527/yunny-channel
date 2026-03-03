package com.yunny.channel.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Slf4j
public class FileAppendCsv {

  public static void toCsv(String file, List<String> recordList) {
    if (CollectionUtils.isEmpty(recordList)) {
      return;
    }
    createFile(file);
    try (BufferedWriter out = new BufferedWriter(new FileWriter(file, true))) {
      for (String csvRecord : recordList) {
        out.write(csvRecord);
        out.newLine();
      }
      // 刷新输出流以确保所有记录都已写入文件
      out.flush();
    } catch (IOException e) {
      // 如果BufferedWriter的创建或关闭出现问题，记录错误
      log.error("IOException while creating/closing BufferedWriter:", e);
    }
  }

  private static void createFile(String file) {
    try {
      File csvFile = new File(file);
      if (!csvFile.exists()) {
        csvFile.createNewFile();
      }
    } catch (Exception e) {
      log.error("创建文件抛出异常", e);
    }
  }
}
