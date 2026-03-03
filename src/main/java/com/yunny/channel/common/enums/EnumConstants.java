package com.yunny.channel.common.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

public class EnumConstants {

    /**
     * redis数据类型
     */
    public enum RedisDataType {
        STRING("string"), LIST("list"), HASH("hash"), SET("set"), ZSET("zset");

        private String value;

        RedisDataType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        /**
         * 通过value值取枚举对象
         *
         * @param value
         * @return
         */
        public static RedisDataType getTypeByValue(String value) {
            if (StrUtil.isBlank(value)) {
                return null;
            }
            for (RedisDataType type : RedisDataType.values()) {
                if (type.getValue().equals(value)) {
                    return type;
                }
            }
            return null;
        }
    }

    /**
     * 是与否的公共枚举
     */
    @Getter
    public enum IsNotEnum {
        YES(1, "是"),
        NO(2, "否");

        private Integer state;

        private String info = "";

        IsNotEnum(Integer state, String info) {
            this.state = state;
            this.info = info;
        }
    }



}
