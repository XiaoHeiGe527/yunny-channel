package com.yunny.channel.common.constant;

/**
 * @Description
 * @Author hex
 * @CreateDate 2020/3/20 10:17
 */
public class OrderConstant {

    /**
     * 微信返回标识
     */
    public static final String RETURN_CODE = "return_code";

    /**
     * 微信返回标识
     */
    public static final String RESULT_CODE = "result_code";

    /**
     * 微信返回成功
     */
    public static final String SUCCESS = "SUCCESS";

    /**
     * 支付宝返回成功
     */
    public static final String TRADE_SUCCESS = "TRADE_SUCCESS";

    /**
     * 支付宝返回成功结束
     */
    public static final String TRADE_FINISHED = "TRADE_FINISHED";

    /**
     * 锁的key payLog的crystal_order_no
     */
    public static final String LOCK_PAY_LOG_CRYSTAL_ORDER_NO = "lock:pay_log:crystal_order_no:";

    /**
     * 锁的key duration_order的编号
     */
    public static final String LOCK_DURATION_ORDER_NO = "lock:duration_order:order_no:";

    /**
     * 时长子订单编号前缀
     */
    public static final  String DURATION_SUB_ORDER_NO_PREFIX = "AYXSDD";

    /**
     * 充值订单前缀
     */
    public static final String CHARGE_CONSIGNMENT_ORDER_NO_PREFIX = "AYCZ";



    /**
     * 锁的key device_no的编号
     */
    public static final String LOCK_DEVICE_NO = "lock:device:device_no:";

    /**
     * 结束
     */
    public static final int ORDER_STATE_OVER = 0;

    /**
     * 正常
     */
    public static final int ORDER_STATE_RUNNING = 1;

    /**
     * 停用
     */
    public static final int ORDER_STATE_STOP = 2;

    /**
     * 分配中
     */
    public static final int ORDER_STATE_ALLOCATING = 3;

    /**
     * 待支付
     */
    public static final int ORDER_STATE_WAIT_PAY = 4;
}
