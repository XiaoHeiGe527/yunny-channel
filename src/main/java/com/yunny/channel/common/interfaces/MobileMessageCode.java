package com.yunny.channel.common.interfaces;


/**
 * @author Mr. Du
 * @explain
 * @createTime 2019/12/20 15:15
 * @motto If you would have leisure, do not waste it.
 */

public interface MobileMessageCode {

    long INCR_NUMBER=1L;

    int DEFAULT_SHORT_MESSAGE_PLATFORM=0;

    int LEXIN_SHORT_MESSAGE_PLATFORM=1;

    int ALIYUN_SHORT_MESSAGE_PLATFORM=2;

    int DEFAULT_SHORT_MESSAGE_STATE=1;

    /**
     * 新增已发送失败
     */
    int FAIL_SHORT_MESSAGE_STATE=2;

    /**
     * 短信请求以被第三方受理
     */
    int SUCCESSFUL_ACCEPTANCE_MESSAGE_STATE = 5;

    int DEFAULT_SECURITY_CODE_LENGTH=6;

    int DEFAULT_NUMBER_LENGTH=6;

    int DEFAULT_NUMBER_LENGTH_4=4;

    String BJ_STRING="北京";

    String SH_STRING="上海";

    String TJ_STRING="天津";

    String CQ_STRING="重庆";

    String CITY_STRING="市";

    String PROVINCE_STRING="省";

    String DEFAULT_AREA_STRING="未知";

    Integer DEFAULT_AREA_LEVEL_PROVINCE = 1;

    Integer DEFAULT_AREA_LEVEL_CITY = 2;


    Integer DEFAULT_CODE_TYPE_REGISTER = 1;

    Integer DEFAULT_CODE_TYPE_UPDATE = 2;

    Integer DEFAULT_CODE_TYPE_BINDING = 3;

    //登录
    Integer DEFAULT_CODE_TYPE_LOGIN = 4;

    /**
     *     忘记密码 不需要token验证
     */
    Integer DEFAULT_CODE_TYPE_UPDATE_NO_TOKEN =5;
    /**
     * 检验手机号
     */
    Integer DEFAULT_CODE_TYPE_INSPECT = 6;
    /**
     * 虚机到期提醒短信
     */
    Integer VM_SMSS_REMINDER = 7;
    /**
     * 忘记二级密码
     */
    Integer DEFAULT_CODE_TYPE_FORGET_SECPWD = 8;

    /**
     * 【企管】邀请员工加入企业
     */
    Integer DEFAULT_CODE_CM_EMPLOYEE_INVITATION = 20;




    /**
     * 用户登录绑定第三方（微信）验证码短信类型
     */
    Integer THIRD_PARTYBINDING_NODE = 8;

    String ENCRYPT_STRING = "AOYOU2020";

    /**
     * 用户+token+登录方式+用户id
     */
    String USER_TOKEN_KEY_PREFIX_REDIS = "user:token:";

    /**
     * 用户+token+登录方式+用户id
     */
    String USER_NO_KEY_PREFIX_REDIS="user:no:";

    String NOTICE_PREFIX="AYZNTZ";

    String NOTICE_PREFIX_INVITE="AYYQXX";

    String NOTICE_INCR_PREFIX="notice:";

    String USER_INCR_PREFIX="user:";

    String USER_NICK_NAME_INCR_PREFIX="user:nick:";

    String USER_PREFIX="u";

    String USER_NICK_NAME_PREFIX="yunny-";

    String NODE_LIMIT_PREFIX="mobile:limit";

    int UNIQUENESS_NUMBER=1;

    int SALES_BY_PROXY_CODE=0;

    int GROUP_BUYING_CODE=1;

    //String DURATION_ORDER_PREFIX_REDIS="duration:order:";

    String DURATION_ORDER_PREFIX = "AYSPSC";

//    int CHARGE_UNIT_MINUTE=0;
//
//    int CHARGE_UNIT_HOUR=1;

    int CHARGE_UNIT_DAY=1;

    int CHARGE_UNIT_MONTH=2;

    int CHARGE_UNIT_YEAR=3;

    int MANUAL_ORDER_SYSTEM_TYPE=3;

    int SHOP_ORDER_TYPE=2;

    int ACTIVATE_STATE=1;

    int NOT_ACTIVATE_STATE=0;

    /**
     * 双无
     */
    int DOUBLE_NO_STRATEGY=0;
    /**
     * 双有
     */
    int DOUBLE_A_STRATEGY=1;
    /**
     * 免密
     */
    int WITH_CODE_STRATEGY=2;

    String SYSTEM_USER_TOKEN_KEY_PREFIX_REDIS="system:user:token:";

    /**
     * 后台用户+token+登录方式+用户id
     */
    String SYSTEM_USER_NO_KEY_PREFIX_REDIS = "system:user:no:";

    String VERIFY_IGNORE_PATH_SUFFIX = "/login";

    String PLATFORM_TAG_USER = "1";

    String PLATFORM_TAG_ADMIN = "2";

    Integer BOX_CHANNEL = 1;

    Integer BOOK_CHANNEL = 5;

    String LOCK_USER_PROPERTY = "lock:user_property:user_no:";

    Integer UNIQUELY_IDENTIFIES = 1;

    Integer DEFAULT_USER_TYPE = 0;

    Integer WHITE_USER_TYPE = 1;

    Integer BLACK_USER_TYPE = 2;
}
