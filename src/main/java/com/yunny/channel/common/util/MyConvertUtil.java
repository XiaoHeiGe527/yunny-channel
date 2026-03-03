package com.yunny.channel.common.util;

import cn.hutool.core.util.NumberUtil;

import java.math.BigDecimal;

/**
 * ClassName: MyConvertUtil
 * Description: 类型转换工具
 *
 * @author shengfq
 * @date: 2022/11/21 6:56 下午
 */
public class MyConvertUtil {
   static BigDecimal tenThousand=new BigDecimal(10000);

   /**
    * 格式化输出金额
    * */
    public static String convertMoney(BigDecimal source){
        String value="";
        if(source!=null){
            if(NumberUtil.isGreater(source,tenThousand)) {
                value=(NumberUtil.decimalFormat("#.00万元",NumberUtil.div(source,tenThousand).doubleValue()));
            }else{
                value=NumberUtil.decimalFormat("#.00元",source.doubleValue());
            }
        }else{
            value=BigDecimal.ZERO.toString();
        }
        return value;
    }
    /**
     * 格式化输出统计百分比
     * */
    public static String convertPercentages(BigDecimal source){
        String value="";
        if(source!=null){
            if(NumberUtil.isGreater(source,BigDecimal.ONE)) {
                value=NumberUtil.decimalFormat("#.##%", source.doubleValue());
            }else{
                value=NumberUtil.decimalFormat("#.##%",source.doubleValue());
            }
        }else{
            value=BigDecimal.ZERO.toString();
        }
        return value;
    }
}
