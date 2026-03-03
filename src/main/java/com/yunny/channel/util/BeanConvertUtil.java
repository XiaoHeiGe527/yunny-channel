package com.yunny.channel.util;
import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 对象转换工具类（BeanConvertUtil）
 * 封装重复的 DO/DTO/VO 转换逻辑（替代多次BeanUtils.copyProperties）：
 * @ClassName BeanConvertUtil
 * @Author sunfuwei521@qq.com
 * @Date 2025/11/4 14:08
 */
@Slf4j
public class BeanConvertUtil {
    /**
     * 转换单个对象
     */
    public static <T, S> T convert(S source, Class<T> targetClass) {
        if (source == null) return null;
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            log.error("对象转换失败，源类型：{}，目标类型：{}", source.getClass(), targetClass, e);
            throw new RuntimeException("对象转换异常", e);
        }
    }

    /**
     * 转换集合
     */
    public static <T, S> List<T> convertList(List<S> sourceList, Class<T> targetClass) {
        if (CollectionUtil.isEmpty(sourceList)) return CollectionUtil.newArrayList();
        return sourceList.stream()
                .map(source -> convert(source, targetClass))
                .collect(Collectors.toList());
    }
}