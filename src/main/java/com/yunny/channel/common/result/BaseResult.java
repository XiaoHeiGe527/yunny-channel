package com.yunny.channel.common.result;


import com.yunny.channel.common.constant.ExceptionConstants;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author hex
 * @Description result
 * @Date 16:16 2019/7/2
 */
@Data
@NoArgsConstructor
public class BaseResult<T> implements Serializable {

	/**
	 * 200 is success other is failure
	 */
	private Integer code;

	/**
	 * code = 200 then success
	 */
	private String message;

	/**
	 * your data
	 */
	private T data;

	/**
	 * all params
	 * @param code
	 * @param message
	 * @param data
	 */
	public BaseResult(final int code, final String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	/**
	 * no param success
	 * @return
	 */
	public static BaseResult success(){
		return success(null);
	}

	/**
	 * data param success
	 * @return
	 */
	public static BaseResult success(final Object data){
		return new BaseResult(ExceptionConstants.RESULT_CODE_SUCCESS, ExceptionConstants.RESULT_SUCCESS,data);
	}

	/**
	 * code message params failure
	 * @return
	 */
	public static BaseResult failure(final int code, final String message){
		return failure(code, message,null);
	}

	/**
	 * all params failure
	 * @return
	 */
	public static BaseResult failure(final int code, final String message, final Object data){
		return new BaseResult(code, message,data);
	}
}
