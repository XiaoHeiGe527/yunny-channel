package com.yunny.channel.common.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 暂时只支持写在属性上  写在类上统一处理待优化
 * @Author hex
 * @CreateDate 2020/4/21 10:53
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NullToJsonSerializer {
}
