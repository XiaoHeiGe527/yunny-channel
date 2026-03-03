package com.yunny.channel.common.util;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.yunny.channel.common.exception.MyException;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

public class CodeUtil {

    // 使用到Algerian字体，系统里没有的话需要安装字体，字体只显示大写，去掉了1,0,i,o几个容易混淆的字符
    public static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";

    /**
     * 生成uuid
     */
    public static String getUUID() {
        return IdUtil.fastSimpleUUID();
    }

    /**
     * 使用系统默认字符源生成验证码
     *
     * @param length 验证码长度
     * @return
     */
    public static String getCode(int length) {
        return getCode(length, VERIFY_CODES);
    }

    /**
     * 使用指定源生成验证码
     *
     * @param length  验证码长度
     * @param sources 验证码字符源
     * @return
     */
    public static String getCode(int length, String sources) {
        if (StrUtil.isNotBlank(sources)) {
            sources = VERIFY_CODES;
        }

        StringBuilder code = new StringBuilder(length);
        int codesLen = sources.length();
        for (int i = 0; i < length; i++) {
            code.append(sources.charAt(RandomUtil.randomInt(codesLen)));
        }
        return code.toString();
    }

    /**
     * 输出指定验证码图片流
     *
     * @param width    图片宽
     * @param height   图片高
     * @param response
     */
    public static String outputImg(int width, int height, HttpServletResponse response) {
        try {
            // 定义图形验证码的长、宽、验证码字符数、干扰线宽度
            LineCaptcha captcha = CaptchaUtil.createLineCaptcha(width, height, 4, 10);
            captcha.setBackground(new Color(100, 149, 237));
            // 图形验证码写出，可以写出到文件，也可以写出到流
            captcha.write(response.getOutputStream());
            return captcha.getCode();
        } catch (IOException e) {
            throw new MyException("获取图片验证码失败");
        }
    }

}
