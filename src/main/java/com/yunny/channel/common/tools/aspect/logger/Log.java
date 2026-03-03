package com.yunny.channel.common.tools.aspect.logger;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 1：普通业务操作信息（一般）2：涉及公民个人信息（重要）3：涉及个人隐私信息（敏感）4：涉及侦查手段等高敏信息（特殊）
     * @return
     */
    public int dataLevel();

    /**
     * 操作类型（0：登录；1：查询；2：新增；3：修改；4：删除；5：登出 ；6：导出 ） 和审计一致无需特殊处理
     */
    public int operateType();

    public String operateName();

    public String operate();

}