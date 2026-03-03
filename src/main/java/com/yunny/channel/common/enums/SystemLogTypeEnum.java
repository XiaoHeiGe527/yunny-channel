package com.yunny.channel.common.enums;

import lombok.Getter;

@Getter
public enum SystemLogTypeEnum {


    //ENT '操作类型 增加 1 修改 2 删除3',
    /**
     * 验证获取频率太高
     */
    ADD(1, "增加"),
    UPDATE(2, "修改"),
    DOWNLOAD(4, "下载"),
    DELETE(3, "删除")
    ;

    SystemLogTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;
}
