package com.yunny.channel.common.constant;


import java.util.Arrays;
import java.util.List;

public class ServiceConstants {

    public static final String STATUS = "status";

    public static final int DEFAULT_NUMBER_LENGTH = 6;

    public static final String  DEFAULT_PASSWORD  = "0000";

    public static final String   PASSWORD_SALT = "AOYOU8083";


    public static final String   USER_NO_PREFIX = "YG";


    public static final Integer EXCEL_MAXIMUM = 2000;

    /**
     * 需要token过滤的接口
     */
    public static final String   TOKEN_FILTER_URL  =
        "/systemRole/*,/systemUser/*,/systemResource/*," +
        "/systemUserResource/*,/systemUserRole/*," +
        "/network/*,/productInfo/*,/productPackage/*,/salesOrder/*,/problemOrder/*,/effectiveOrder/*,/mobilecloud/*," +
        "/dataReport/*,/salesOrderTemp/*";



    public static final  List<String> MANAGED_CAR_NUMBERS = Arrays.asList(
            "黑KA8767", "黑KA7713", "黑KJ6988", "黑KA4851",
            "黑KA6540", "黑KA6406", "黑KA8838", "黑KA9790",
            "黑KA7571", "黑KA9409", "黑KA9747", "黑KA8132",
            "黑KA2626", "黑KA1826", "黑KA1821", "黑KA6409",
            "黑KA7817", "黑KA7393"
    );

}
