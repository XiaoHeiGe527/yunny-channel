package com.yunny.channel.common.util;

import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.StringEscapeUtils;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;




/**
 * @author Mr. Du
 * @version 0.0.1
 * @date Jul 26, 2018 8:06:29 PM
 */
public class StringUtil {

    public static final boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    public static final String localIp() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin) {
        return MD5Encode(origin, "utf-8");
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            else resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};


    /**
     * 按照指定字符 切割
     * @param str 字符串
     * @param regex 分割点
     * @return
     */
    public static  ArrayList<String> split(String str,String regex){
        ArrayList  strList = new ArrayList();
        if(StringUtil.isEmpty(str)||StringUtil.isEmpty(regex)){
            strList.add(str);

        }else {
            String a []  = str.split(regex);

            for(int i=0;i<a.length;i++){
                strList.add(a[i]);
            }
        }

        return strList;
    }

//
//    public static void main(String[] args) {
//
//        List<String> imagesList = new ArrayList<>();
//        imagesList.add("\\mydata\\zijinchakong\\task\\2024-04-18/cqbgs_20240418155254_1.png");
//        imagesList.add("\\mydata\\zijinchakong\\task\\2024-04-18/cqbgs_20240418155254_2.png");
//        imagesList.add("\\mydata\\zijinchakong\\task\\2024-04-18/cqbgs_20240418155254_3.png");
//
//        // 使用 JSONUtil.toJsonStr 输出 JSON 字符串
//        String imagesJsonString = JSONUtil.toJsonStr(imagesList);
//
//        // 输出结果
//        System.out.println("imagesJsonString=========[" + imagesJsonString + "]");
//
//
//        String tmp = StringEscapeUtils.unescapeJavaScript(imagesJsonString);
//
//        // 输出结果
//        System.out.println("tmp=========[" + tmp + "]");
//    }

}
