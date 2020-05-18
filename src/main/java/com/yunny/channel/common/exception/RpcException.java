package com.yunny.channel.common.exception;


import com.yunny.channel.common.constant.ExceptionConstants;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @Description
* @Author hex
* @CreateDate 2019/7/10 14:46
*/
@Data
@NoArgsConstructor
public class RpcException extends BaseException{

	private Integer code;

	private String message;

	public RpcException(String message){
		this(ExceptionConstants.RESULT_CODE_RPC_ERROR,message);
	}

	public RpcException(int code, String message){
		this.message = message;
		this.code = code;
	}
}
