package com.yunny.channel.sms.config;


import com.yunny.channel.util.MD5;

/**
 * 乐信平台的基本信息
 *  @author Mr. Du
 */
public class LeXinConfig {
//    /**
//     * 用户名(乐信登录账号)
//     */
//
//    private  String accName;
//
//    /**
//     * 密码(乐信登录密码32位MD5加密后转大写，如123456加密完以后为：E10ADC3949BA59ABBE56E057F20F883E)
//     */
//
//    private  String accPwd;
//
//
//
//    private  String thirdparty;

    public String getThirdparty() {
        //乐信
        return "1";
    }
    public String getAccName() {
        return "minyang@yunzhiqu.com";
    }


    public String getAccPwd() {
        //  yzqdev@163.com
        return MD5.encryption( "yunzhiqu@9999").toUpperCase();
    }

    public static LeXinConfig build(){
        return new LeXinConfig();
    }

}
