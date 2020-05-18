package com.yunny.channel.common.exception;

import com.yunny.channel.common.constant.ExceptionConstants;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @Description 
* @Author hex
* @CreateDate 2019/7/19 15:41
*/
@Data
@NoArgsConstructor
public class ServiceException extends BaseException {

	private Integer code;

	private String message;

	public ServiceException(String message){
		this(ExceptionConstants.RESULT_CODE_SERVICE_ERROR,message);
	}

	public ServiceException(int code, String message){
		this.message = message;
		this.code = code;
	}
}
