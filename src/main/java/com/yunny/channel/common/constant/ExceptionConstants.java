package com.yunny.channel.common.constant;

/**
* @Description 异常全局
* @Author hex
* @CreateDate 2019/7/8 14:26
*/
public class ExceptionConstants {

	/** 返回成功message */
	public static final String RESULT_SUCCESS = "success";

	/** 返回失败message,一般需要自定义 */
	public static final String RESULT_FALSE = "false";

	/** 返回网络异常 */
	public static final String RESULT_NETWORK_ERROR = "网络异常";

	/** 返回成功code */
	public static final int RESULT_CODE_SUCCESS = 200;

	/** 返回无权限code */
	public static final int RESULT_CODE_UNAUTHORIZED = 401;

	/** 返回错误code */
	public static final int RESULT_CODE_ERROR = 500;

	/** 返回基本默认错误code */
	public static final int RESULT_CODE_BASE_ERROR = 1000;

	/** 返回业务默认错误code */
	public static final int RESULT_CODE_SERVICE_ERROR = 2000;

	/** 返回业务错误-参数不合法code */
	public static final int RESULT_CODE_PARAM_ERROR = 2001;

	/** 返回业务错误-对应记录已存在code */
	public static final int RESULT_CODE_DATA_EXIST_ERROR = 2002;

	/** 返回业务错误-获取微信公众号openid失败code */
	public static final int RESULT_CODE_WX_PUBLIC_OPENID_NOT_EXIT_ERROR = 2003;

	/** 返回RPC默认错误code */
	public static final int RESULT_CODE_RPC_ERROR = 3000;

	/** 返回参数错误code */
	public static final int RESULT_CODE_ARGUMENT_ERROR = 4000;

	/** 服务器异常，无法识别的异常，尽可能对通过判断减少未定义异常抛出 */
	public static final int RESULT_CODE_SERVER_ERROR = 9999;

	/** ica获取失败 */
	public static final int ICA_SERVER_FAIL = 4001;

	/** 激活卡激活虚机请求的端 不是盒子端 */
	public static final int ASSIGN_VM_NO_BOX = 4002;
}
