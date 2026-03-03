package com.yunny.channel.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.ServiceException;

import com.yunny.channel.common.config.oss.AliyunOssUtils;
import com.yunny.channel.common.exception.BaseException;
import com.yunny.channel.common.vo.UploadFileVO;
import com.yunny.channel.oss.config.AliyunOssConfig;
import com.yunny.channel.oss.service.OssService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import static com.yunny.channel.common.enums.OSSCodeEnum.FILE_UPLOAD_FAILED;


/**
* @Description 
* @Author hex
* @CreateDate 2019/12/30 14:37
*/
@Slf4j
@Service
public class OssServiceImpl implements OssService {

	/**
	 * 上传图片格式
	 */
	@Value("#{'${oss.image.types}'.split(',')}")
	private List<String> imageTypeList;

	@Autowired
	private OSS ossClient;

	@Autowired
	private AliyunOssConfig aliyunOssConfig;

	@Autowired
	AliyunOssUtils aliyunOssUtils;

	/**
	 * @param uploadFile
	 * @return
	 */
	@Override
	public UploadFileVO uploadFile(MultipartFile uploadFile, int type) {
		boolean isLegal = false;
		if(type == 0){
			// 校验图片格式
			for (String imageType : imageTypeList) {
				if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), imageType)) {
					isLegal = true;
					break;
				}
			}
		}else {
			isLegal = true;
		}
		if (!isLegal) {
			throw new ServiceException("图片格式不正确");
		}
		String fileName = uploadFile.getOriginalFilename();
		String filePath = getFilePath(fileName);
		try {
			ossClient.putObject(aliyunOssConfig.getBucketName(), filePath, new ByteArrayInputStream(uploadFile.getBytes()));
		} catch (Exception e) {
			log.error("aliyun oss上次文件失败，失败原因为",e);
			throw new BaseException(FILE_UPLOAD_FAILED.getCode(),FILE_UPLOAD_FAILED.getMessage());
		}
		UploadFileVO uploadFileVO = new UploadFileVO();
		uploadFileVO.setUrl(filePath);
		return uploadFileVO;
	}

	/**
	 * 生成文件路径
	 * @param sourceFileName
	 * @return
	 */
	private String getFilePath(String sourceFileName) {
		LocalDateTime localDateTime = LocalDateTime.now();
		return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
				+ "/"
				+ System.currentTimeMillis() +
				new Random().nextInt(10000) + "." +
				StringUtils.substringAfterLast(sourceFileName, ".");
	}
}
