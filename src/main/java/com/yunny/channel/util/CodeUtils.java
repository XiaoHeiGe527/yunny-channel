package com.yunny.channel.util;

import java.util.Random;

/**
 * @author Mr. Du
 * @explain
 * @createTime 2019/12/20 14:35
 * @motto The more learn, the more found his ignorance.
 */

public class CodeUtils {

    /**
     * 手机随机验证码
     * @param len
     * @return
     */
    public static String getNumberRandom(int len){
        Random r = new Random();
        int bitNum=1;
        for(int i=0;i<len;i++){
            bitNum=bitNum*10;
        }
        long num = Math.abs(r.nextLong() % (bitNum));
        String s = String.valueOf(num);
        for (int i = len - s.length(); i >0 ;i--) {
            s = "0" + s;
        }
        if(s.length()>len){
            s=s.substring(0,len);
        }
        return s;
    }
}

