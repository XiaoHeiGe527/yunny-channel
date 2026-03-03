package com.yunny.channel.oss.controller;


import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.UploadFileVO;
import com.yunny.channel.oss.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description
 * @Author hex
 * @CreateDate 2019/12/25 17:30
 */
@RestController
@Slf4j
@RequestMapping
public class OssController {

	@Autowired
	private OssService ossService;

	/**
	 * 阿里OSS文件上传
	 * @param uploadFile
	 * @param type
	 * @return
	 */
	@PostMapping("/upload")
	public BaseResult uploadFile(@RequestParam("file") MultipartFile uploadFile, @RequestParam("type") int type) {
		UploadFileVO uploadFileVO = ossService.uploadFile(uploadFile,type);
		return BaseResult.success(uploadFileVO);
	}
}
