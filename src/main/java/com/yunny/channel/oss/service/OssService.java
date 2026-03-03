package com.yunny.channel.oss.service;

import com.yunny.channel.common.vo.UploadFileVO;
import org.springframework.web.multipart.MultipartFile;

/**
* @Description 
* @Author hex
* @CreateDate 2019/12/30 15:00
*/
public interface OssService {

	/**
	 * 上传文件
	 * @param uploadFile
	 * @param type
	 * @return
	 */
	UploadFileVO uploadFile(MultipartFile uploadFile, int type);
}
