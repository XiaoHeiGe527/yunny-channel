package com.yunny.channel.common.enums;

import lombok.Getter;

/**
 * @author Mr. Du
 * @explain
 * @createTime 2020/4/26 17:38
 * @motto The more learn, the more found his ignorance.
 */
@Getter
public enum UserCodeEnum {

    /**
     * 验证获取频率太高
     */
    NODE_RECODE_NO_SEND(10110001, "请您不要重复操作"),
    SYSTEM_USER_IS_ILLEGAL(10110002, "系统用户存在非法用户"),
    SYSTEM_USER_ALREADY_EXISTS(10110003, "系统用户已存在"),
    SYSTEM_USER_DOES_NOT_EXIST(10110004, "系统用户不存在"),
    VERIFICATION_CODE_IS_INVALID(10110005, "无效的验证码"),
    PHONE_VERIFICATION_CODE_DOES_NOT_MATCH(10110006, "手机与当前用户不匹配"),
    THE_VERIFICATION_CODE_DOES_NOT_MATCH_THE_PHONE(10110007, "验证码与手机不匹配"),
    DUPLICATE_ROLES_IN_THE_GROUP(10110008, "组内角色重复"),
    SUPER_ADMINISTRATOR_CANNOT_MODIFY(10110009, "超级管理员不能修改"),
    PERMISSION_DOES_NOT_EXIST(10110010, "该权限不存在"),
    PERMISSION_LEVELS_DO_NOT_MATCH(10110011, "权限层级不匹配"),
    USER_IS_NOT_REGISTERED(10110012, "用户没有注册"),
    USER_OR_PASSWORD_IS_INCORRECT(10110013, "用户或密码不正确"),
    USER_DISABLED(10110014, "用户已被禁用"),
    USER_DELETED(10110022, "用户已经被删除"),
    NO_DEVICE_NO(10110015, "没有可用的串号"),
    UNUSUAL_EQUIPMENT(10110016, "非常用设备"),
    INSERT_DATA_RECORD_EXCEPTION(10110017, "请求插入活跃明细失败"),
    SMS_REPEAT_RECEPTION_EXCEPTION(10110018, "该用户已发送过提醒短信，请勿重复发送"),
    DELIVERYNUMBER_EXCEPTION(10110019, "生成库存号失败"),
    USER_IS_NOT_BINDING(10110020, "用户没有绑定"),
    USER_IS_NOT_BINDING_MOBILE(10110021, "用户没有绑定手机号"),
    ;


    private Integer code;

    private String message;

    UserCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}

