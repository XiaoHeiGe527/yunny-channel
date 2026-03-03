package com.yunny.channel.common.util;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	
	/**
	 * 获取md5摘要
	 * MessageDigest类为应用程序提供信息摘要算法的功胄1�7
	 * @param strMd5
	 * @return
	 */
	public static String md5(String strMd5){
        String sRet = null;
        try{
		    MessageDigest alga = MessageDigest.getInstance("MD5");
		    alga.update(strMd5.getBytes());
		    byte[] digesta=alga.digest();
		    sRet= byte2hex(digesta);
        }catch (NoSuchAlgorithmException ex) {
            //logger.info("定时任务执行失败,触发时间-->{},错误原因-->{}",new Date(),ExceptionUtil.getPrintStackTraceStr(ex));
        }
            return sRet;
    }

    public static String byte2hex(byte[] b) //二进制转字符丄1�7
    {
        String hs="";
        String stmp="";
        for (int n=0;n<b.length;n++){
            stmp=(Integer.toHexString(b[n] & 0XFF));
            if (stmp.length()==1) hs=hs+"0"+stmp;
                else hs=hs+stmp;
            if (n<b.length-1)  hs=hs+"";
        }
        return hs;
    }

    /**
     * md5 32位加密
     * @param plainText
     * @return
     */
    public final static String encryption(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            //logger.info("执行失败,触发时间-->{},错误原因-->{}",new Date(),ExceptionUtil.getPrintStackTraceStr(e));
        }
        return re_md5;
    }

    /**
     * MD5值计算<p>
     * MD5的算法在RFC1321 中定义:
     * 在RFC 1321中，给出了Test suite用来检验你的实现是否正确：
     * MD5 ("") = d41d8cd98f00b204e9800998ecf8427e
     * MD5 ("a") = 0cc175b9c0f1b6a831c399e269772661
     * MD5 ("abc") = 900150983cd24fb0d6963f7d28e17f72
     * MD5 ("message digest") = f96b697d7cb7938d525a2f31aaf161d0
     * MD5 ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b
     *
     * @param res 源字符串
     * @return md5值
     */
    public final static String md5Digest(String res) {
        if(res ==null||"".equals(res)){
            return null;
        }
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        byte[] strTemp;
        try {
            strTemp = res.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            return null;
        }
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            String dd = new String(str);
            return dd;
        } catch (Exception e) {
            //logger.info("定时任务执行失败,触发时间-->{},错误原因-->{}",new Date(),ExceptionUtil.getPrintStackTraceStr(e));
            return null;
        }
    }

    /**
     * MD5值计算+Base64<p>
     * MD5的算法在RFC1321 中定义:
     * 在RFC 1321中
     *
     * @param res 源字符串
     * @return md5值
     */
    public final static byte[] md5SrcDigest(String res) {
        if(res == null || "".equals(res)){
            return null;
        }
        byte[] strTemp = res.getBytes();
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            return md;
        } catch (Exception e) {
            //logger.info("定时任务执行失败,触发时间-->{},错误原因-->{}",new Date(),ExceptionUtil.getPrintStackTraceStr(e));
            return null;
        }
    }
    public static void main(String[] argv) {
    	// admin pwd
//    	System.out.println(MD5.md5(MD5.md5("123456") + "9c8b77"));
    	System.out.println(MD5.md5("appid=wxe504c19963329f4c&body=消费&mch_id=1244254902&nonce_str=wx23GB0Ifwefzkelsewe&notify_url=http://app.wanwan.im/pay!wnotify.htm&openid=oQhYYtw2Vjah1_pLkJfWw3ROycLE&out_trade_no=002017070616440022&spbill_create_ip=192.168.1.1&total_fee=100&trade_type=JSAPI&key=BDkjwx992kl321lkflwejnfewf3se21g").toUpperCase());
//    	System.out.println(MD5.md5("appid=wx70d02037dcf85071&body=【特价套餐】6-8人人头马套餐&mch_id=1245779202&nonce_str=wx23GB0Ifwefzkelsewe&notify_url=http://app.wanwan.im/pay!wxtrade.htm&out_trade_no=T2015060914252813143&spbill_create_ip=192.168.1.1&total_fee=1&trade_type=APP&key=ashdzh12dsahf5dn8dsjf09jslahf133").toUpperCase());
    }
}
