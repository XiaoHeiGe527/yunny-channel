package com.yunny.channel.service.ftp;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author sunfuwei
 */
public interface FtpService {

    boolean uploadFileToFtp(MultipartFile file, String remoteFileName);

    String handleFileUpload(MultipartFile multipartFile, String fileLabel);

    boolean downloadFile(String remoteFileName, HttpServletResponse response,String path);

}
