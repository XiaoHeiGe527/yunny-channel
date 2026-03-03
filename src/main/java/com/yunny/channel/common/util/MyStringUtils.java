package com.yunny.channel.common.util;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

import com.yunny.channel.common.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 描述：字符串工具类
 * @author shengfq
 * @date 2022-09-20
 **/
@Slf4j
public class MyStringUtils {
    private final static Pattern P = Pattern.compile("^[-\\+]?[\\d]*$");

    private final static Pattern telPattern = Pattern.compile(".+(\\d{11}).*");

    /**
     * 描述：隐藏字符串中间n位
     * 时间：2020/5/12 14:54
     *
     * @param text  待操作字符串
     * @param start 隐藏开始下标
     * @param end   隐藏结束下标
     * @return 隐藏字符串中间n位
     */
    public static String hiddenChars(String text, int start, int end) {
        if (null != text && text.length() > end) {
            char[] chars = text.toCharArray();
            for (int i = start; i < end; i++) {
                chars[i] = '*';
            }
            text = new String(chars);
        }
        return text;
    }

    /**
     * 隐藏手机号中间4位
     *
     * @param text 号码
     * @return
     */
    public static String hiddenTel(String text) {
        return hiddenChars(text, 3, 7);
    }

    /**
     * 替换掉str中的手机号中间4位
     *
     * @param str
     * @return
     */
    public static String hiddenTelFromStr(String str) {
        if (StrUtil.isBlank(str)) {
            return str;
        }
        Matcher matcher = telPattern.matcher(str);
        if (matcher.find()) {
            String telGroup = matcher.group(1);
            if (StrUtil.isNotBlank(telGroup)) {
                str = str.replace(telGroup, hiddenTel(telGroup));
            }
        }
        return str;
    }

    /**
     * 隐藏身份证出生月和日
     *
     * @param text
     * @return
     */
    public static String hiddenIdCard(String text) {
        return hiddenChars(text, 10, 14);
    }

    /**
     * 隐藏银行卡中间10位
     *
     * @param text
     * @return
     */
    public static String hiddenBankCard(String text) {
        return hiddenChars(text, 5, 15);
    }

    /**
     * 隐藏IP第4段
     *
     * @param ip
     * @return
     */
    public static String hiddenIp(String ip) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(ip)) {
            ip = ip.substring(0, ip.lastIndexOf(".") + 1).concat("*");
        }
        return ip;
    }


    /**
     * 安全合规的前端显示用户名（隐藏用户名中间字符）
     *
     * @param userName
     * @return 隐藏用户名中间字符后的字符串
     */
    public static String userNameSafe(String userName){
        if(null==userName || userName.isEmpty()) {
            return "";
        }
        if(userName.length()==1){
            return userName;
        }
        if(userName.length() == 2){
            return userName.substring(0, 1)+"*";
        }
        return userName.replaceAll("(\\S)(\\S+)(\\S)", "$1**$3");
    }

    /**
     * 生成MD5
     *
     * @param text
     * @return
     */
    public static String md5(String text) {
        return DigestUtils.md5DigestAsHex(text.getBytes());
    }

    /**
     * 描述：将传入的对象中的全部字符串属性去掉左右空格
     *
     * @param object 待操作的对象
     */
    public static void stringPropertyTrim(Object object) {
        if (null == object) {
            return;
        }
        try {
            Class clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            if (null != fields && fields.length > 0) {
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getType().equals(String.class)) {
                        String value = (String) field.get(object);
                        if (null != value) {
                            field.set(object, value.trim());
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.warn("去左右空格异常：", e);
        }
    }

    /**
     * @description 字符串转数字，至少为0
     * @author shengfq
     * @date 2020/12/10
     */
    public static Long strConvertLong(String str) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(str)) {
            if (P.matcher(str).matches()) {
                return Long.parseLong(str);
            }
        }
        return 0L;
    }

    /**
     * 去掉特定字符marks后的所有字符
     *
     * @param str
     * @return java.lang.String
     * @author shengfq
     * @date 12:02 2021/2/24
     */
    public static String removeMarksBehindStr(String str, String marks) {
        if (StrUtil.isBlank(str) || StrUtil.isBlank(marks)) {
            return str;
        }
        int indexOf = str.indexOf(marks);
        if (indexOf == -1) {
            return str;
        }
        return str.substring(0, indexOf);
    }

    /**
     * 取字符串str中指定字符 strStart、strEnd之间的字符串
     *
     * @param str
     * @return java.lang.String
     * @author shengfq
     * @date 12:02 2021/2/24
     */
    public static String subString(String str, String strStart, String strEnd) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.lastIndexOf(strEnd);

        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0) {
            return str;
        }
        if (strEndIndex < 0) {
            return str;
        }
        /* 开始截取 */
        return str.substring(strStartIndex, strEndIndex).substring(strStart.length());
    }

    /**
     * @description 字符串列表去重并重新排序（仅限纯数字）
     * @author shengfq
     * @date 2021/6/3
     */
    public static String sortStr(String str) {
        return sortStr(str, ",");
    }

    /**
     * @description 字符串列表去重并重新排序（仅限纯数字）
     * @author shengfq
     * @date 2021/6/3
     */
    public static String sortStr(String str, String separate) {
        if (org.apache.commons.lang3.StringUtils.isBlank(str)) {
            return null;
        }
        // 科目ids去重并排序
        List<Long> list = Arrays.asList(str.split(separate)).stream().distinct().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        Collections.sort(list);
        return org.apache.commons.lang3.StringUtils.join(list, separate);
    }

    /**
     * @description 逗号分隔字符串转Long集合
     * @author shengfq
     * @date 2021/10/27
     */
    public static List<Long> splitToLongList(String str) {
        if (org.apache.commons.lang3.StringUtils.isBlank(str)) return null;
        return  StrUtil.split(str, CommonConstant.DEFAULT_SEPARATOR,true,true).stream().filter(s -> NumberUtil.isNumber(s)).map(s -> Long.parseLong(s)).collect(Collectors.toList());
    }

    /**
     * @description 逗号分隔字符串转String集合
     * @author shengfq
     * @date 2021/10/27
     */
    public static List<String> splitToList(String str) {
        if (org.apache.commons.lang3.StringUtils.isBlank(str)) return null;
        return  StrUtil.split(str,CommonConstant.DEFAULT_SEPARATOR,true,true).stream().collect(Collectors.toList());
    }
    /**
     * @description 集合转逗号分隔字符串
     * @author shengfq
     * @date 2022/3/15
     */
    public static String join(Iterable<?> iterable) {
        return org.apache.commons.lang3.StringUtils.strip(org.apache.commons.lang3.StringUtils.join(iterable, CommonConstant.DEFAULT_SEPARATOR));
    }

    /**
     * 除法运算，保留2位小数
     * @param a 被除数
     * @param b 除数
     * @return 商
     */
    public static String div(float a,float b) {
        DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
        return df.format((float)a/b);
    }

    /**
     * 除法运算，返回百分比
     * @param a 被除数
     * @param b 除数
     * @return 商
     */
    public static String ratio(float a,float b) {
       String custRatio = NumberUtil.decimalFormat ("#.##%", a/b);
        return custRatio;
    }

    //转换为万元
    public static String formatJe(BigDecimal je) {
        if(je==null || je.equals(0)){
            return "0";
        }
        return MyStringUtils.div(je.floatValue(),10000);
    }
}
