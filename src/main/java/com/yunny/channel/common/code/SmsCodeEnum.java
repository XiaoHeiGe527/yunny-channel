package com.yunny.channel.common.code;

import lombok.Getter;

/**
 * @author Mr. Du
 * @explain
 * @createTime 2020/4/27 11:15
 * @motto Wisdom gained by experience is better than wisdom gained by learning.
 */
@Getter
public enum SmsCodeEnum {

    FAILED_TO_SEND_VERIFICATION_CODE(10210001, "验证码发送失败"),
    UNKNOWN_VERIFICATION_CODE_ERROR(10210002, "验证码未知错误");

    private Integer code;

    private String message;

    SmsCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
