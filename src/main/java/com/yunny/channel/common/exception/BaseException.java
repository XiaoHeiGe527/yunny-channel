package com.yunny.channel.common.exception;


import com.yunny.channel.common.constant.ExceptionConstants;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @Description 
* @Author hex
* @CreateDate 2019/7/15 14:42
*/
@Data
@NoArgsConstructor
public class BaseException extends RuntimeException{

	private Integer code;

	private String message;

	public BaseException(String message){
		this(ExceptionConstants.RESULT_CODE_BASE_ERROR,message);
	}

	public BaseException(int code, String message){
		this.message = message;
		this.code = code;
	}
}
