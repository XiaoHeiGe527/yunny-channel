package com.yunny.channel.common.constant;

/**
 * 公共常量
 *
 * @author LJ
 */
public interface CommonConstant {

    /**
     * 车辆承保过了11个月进行提示
     */
    public static final Integer REMINDER_MONTHS = 11;


    /**
     * 车辆卡到期前多少天提醒
     */
    public static final Integer VEHICLESOUTWARD_CARD_WARNING_DAY = 5;


    public static final String platform_WEB =  "web";

    /**
     * 请求token
     */
    public static final String TOKEN = "token";
    public static final String apptoken = "RZZX-APPTOKEN";
    public static final String usertoken = "RZZX-USERTOKEN";
    /**
     * 请求密钥
     */
    public static final String CIPHER = "cipher";

    public static final char DEFAULT_SEPARATOR=',';

    /**
     *导出反馈文件zip压缩包中 导出反馈文件的的路径
     */
    public static final String EXPORT_PATH = "/mydata/zijinchakong/export/";

    /**
     * 压缩包类型
     */
    public static final String ZIP = ".zip";
}
