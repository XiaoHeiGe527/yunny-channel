package com.yunny.channel.util;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: yunny-channel
 * @description: VO转换工具类
 * @author: sunfuwei
 * @create: 2024/06/19
 */
public class VOConvertUtils {

    public static <T, R> List<R> convertList(List<T> sourceList, Class<R> targetClass) {
        return sourceList.stream()
                .map(source -> convertSingle(source, targetClass))
                .collect(Collectors.toList());
    }

    private static <T, R> R convertSingle(T source, Class<R> targetClass) {
        R target;
        try {
            target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            throw new RuntimeException("Convert failed", e);
        }
        return target;
    }

    // 使用方式
//    List<UserVO> userVOList = VOConvertUtils.convertList(userList, UserVO.class);
//    List<UserDetailVO> userDetailVOList = VOConvertUtils.convertList(userDetailList, UserDetailVO.class);
}
