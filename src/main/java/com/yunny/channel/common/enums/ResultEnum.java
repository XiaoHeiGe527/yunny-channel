package com.yunny.channel.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应码枚举值
 *
 * @author LIJIAN
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {

    SUCCESS(200, "成功"), FAILURE(1000, "失败"), CIPHER_ERROR(1001, "签名错误"), PARAMETER_ERROR(1002, "参数错误"), NOT_POWER(1003, "未授权"),
    NOT_LOGIN(1004, "未登录"), NOT_DATA(1005, "数据不存在"), SYSTEM_ERROR(9999, "系统繁忙");

    /**
     * 响应代码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String message;

}
