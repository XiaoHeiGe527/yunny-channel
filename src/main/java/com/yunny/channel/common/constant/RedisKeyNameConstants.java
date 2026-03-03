package com.yunny.channel.common.constant;

public class RedisKeyNameConstants {


    /**
     * redis  Key 是否存在
     */
    public static final  String EXIST = "exist";

    /**
     *  存储车辆所有客户信息信息
     */
    public static final  String VEHICLE_CUSTOMERS_LIST="vehicleCustomers:list:";


    /**
     * 档案位置表
     */
    public static final  String EMPLOYEE_FILE_POSITION_LIST="employeeFile:filePosition:list:";


    /**
     * 员工岗位列表
     */
    public static final  String EMPLOYEE_PROFESSION_LIST="employeeFile:profession:list:";


    /**
     * 流程名称
     */
    public static final  String PLAN_TYPE="PLAN:TYPE:list:";



    /**
     *  用户+token+登录方式+用户id
     */
    public static final  String USER_TOKEN_KEY_PREFIX_REDIS="user:token:";

    /**
     * 用户拥有的权限JSON
     */
    public static final  String USER_URL_JSON_KEY_PREFIX_REDIS="user:url:";


    /**
     *  用户登录标识，KEY  用户号， value token
     *  用户登录 检查此用户号是否已经登陆过 是否存在token 如果有则清除历史token 新生成，没有跳过
     */
    public static final  String LOGIN_SIGN="login:sign:";



    /**
     *  后台用户+token+登录方式+用户id
     */
    public static final  String SYSTEM_USER_TOKEN_KEY_PREFIX_REDIS="system:user:token:";

    public static final  String SYSTEM_USER_NO_KEY_PREFIX_REDIS = "system:user:no:";

    public static final  String SYSTEM_USER_NETWORKID_KEY_PREFIX_REDIS = "system:user:networkId:";

    /**
     * 定时将userbaseInfo表信息写入userInfo中的页数KEY
     */
    public static final  String  INSERT_USER_INFO_DATA_JOB_CURRENT_PAGE = "insertUserInfoData:job:currentPage";


    /**
     * 插入车卡数据的时候redis序列号
     */
    public static final  String  INSERT_VEHICLESOUTWARDCARD_NO = "insertVehiclesOutwardCard:no";



    /**
     * 插入车卡数据的时候redis序列号
     */
    public static final  String  VHICLEINSURANCE_NO = "vhicleInsurance:no";

}
