package com.yunny.channel.common.query;

import com.yunny.channel.common.interfaces.OtherGroup;
import com.yunny.channel.common.interfaces.SearchGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YunnyLoginQuery {

    /**
     * 账号
     */
    @NotNull(message = "账号不可为空",groups = {SearchGroup.class})
    private String account;
    /**
     * 密码
     */
    //@NotNull(message = "密码不可为空",groups = {SearchGroup.class})
    private String password;

    /**
     * 校验数据库的密码
     */
    private String passkey;

    private String ipAddress;

    /**
     * 登录平台
     */
    private String  platform;

    /**
     * 更改密码时 用户绑定的手机号
     */
    @NotBlank(message = "手机号不可为空",groups = {OtherGroup.class})
    private String mobile;
    /**
     * 短信验证标识 redis的KEY（MD5）  value 存的是手机号
     */
    private String marking;

    /**
     * 短信验证码
     */
    @NotBlank(message = "验证码不可为空",groups = {OtherGroup.class})
    private String code;

    /**
     * 短信验证方式
     *  1;//用户注册 2;//修改密码 3;//用户绑定 4;//用户登录
     */

    @NotNull(message = "验证方式可为空",groups = {OtherGroup.class})
    private Integer nodeRecodeType;

}
