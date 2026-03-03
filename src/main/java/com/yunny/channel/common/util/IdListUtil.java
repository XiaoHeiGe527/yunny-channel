package com.yunny.channel.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IdListUtil {

    public static String intIdList2String(List<Integer> idList, String regex) {
        if (CollUtil.isNotEmpty(idList)) {
            return CollUtil.join(idList, regex);
        }
        return "";
    }

    public static List<Integer> string2IntIdList(String ids, String regex) {
        if (StrUtil.isNotBlank(ids)) {
            return Arrays.stream(ids.split(regex)).map(Integer::valueOf).collect(Collectors.toList());
        }
        return CollUtil.toList();
    }

    public static String longIdList2String(List<Long> idList, String regex) {
        if (CollUtil.isNotEmpty(idList)) {
            return CollUtil.join(idList, regex);
        }
        return "";
    }

    public static String stringIdList2String(List<String> idList, String regex) {
        if (CollUtil.isNotEmpty(idList)) {
            return CollUtil.join(idList, regex);
        }
        return "";
    }

    public static List<Long> string2LongIdList(String ids, String regex) {
        if (StrUtil.isNotBlank(ids)) {
            return Arrays.stream(ids.split(regex)).map(Long::valueOf).collect(Collectors.toList());
        }
        return CollUtil.toList();
    }

    public static List<String> string2StringIdList(String ids, String regex) {
        if (StrUtil.isNotBlank(ids)) {
            return Arrays.stream(ids.split(regex)).map(String::valueOf).collect(Collectors.toList());
        }
        return CollUtil.toList();
    }
}
