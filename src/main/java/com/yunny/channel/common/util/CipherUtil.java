package com.yunny.channel.common.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.yunny.channel.common.exception.MyAssert;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * 加密解密工具
 *
 * @author LJ
 */
public class CipherUtil {

    /**
     * AES加密
     *
     * @param key     密钥长度16位
     * @param content 要加密内容
     * @return
     */
    public static String aesEncrypt(String key, String content) {
        MyAssert.isTrue(key != null && key.length() == 16, "密钥长度错误");
        // 生成密钥
        SecretKey secretKey = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue(), key.getBytes());
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, secretKey);
        // 加密为16进制表示
        return aes.encryptHex(content);
    }

    /**
     * AES解密
     *
     * @param key        密钥长度16位
     * @param encryptStr 要解密的内容
     * @return 解密失败返回NULL
     */
    public static String aesDecrypt(String key, String encryptStr) {
        MyAssert.isTrue(key != null && key.length() == 16, "密钥长度错误");
        MyAssert.isTrue(StrUtil.isNotBlank(encryptStr), "要解密的内容不能为空");
        try {
            // 生成密钥
            SecretKey secretKey = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue(), key.getBytes());
            SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, secretKey);
            // 解密为字符串
            return aes.decryptStr(encryptStr, CharsetUtil.CHARSET_UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * MD5密码生成
     *
     * @param password
     * @return
     */
    public static String getPwdMD5(String password) {
        return DigestUtil.md5Hex(password.trim()).toUpperCase();
    }

    public static void main(String[] args) {
        String key = "abcdef0123456789"; // 16位
        Date date = DateUtil.date();
        System.out.println(date.getTime());
        String cipherStr = aesEncrypt(key, String.valueOf(date.getTime()));
        System.out.println(cipherStr);
        key = "abcdef0123456789"; // 16位
        System.out.println(">>>>>  " + aesDecrypt(key, cipherStr));

    }

}
