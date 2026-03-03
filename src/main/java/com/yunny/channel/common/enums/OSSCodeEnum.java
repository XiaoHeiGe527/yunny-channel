package com.yunny.channel.common.enums;

/**
 * @author kai
 * @date 2020/4/26 18:52
 * @description:
 */
public enum OSSCodeEnum {

    /**
     * OSS 模块统一的状态吗 1031  前缀
     */

    FILE_UPLOAD_FAILED(10310001,"aliyun oss上传文件失败");

    private Integer code;
    private String message;

    OSSCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
