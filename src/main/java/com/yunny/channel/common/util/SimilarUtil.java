package com.yunny.channel.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 相似度Util
 */
@Slf4j
public class SimilarUtil {

    /**
     * List集合中字符串相似度比例
     *
     * @param list
     * @param minSimilar 最小相似度
     * @return
     */
    public static double getSimilar(List<String> list, double minSimilar) {
        if (CollUtil.isEmpty(list) || list.size() <= 1) {
            return 0;
        }
        int total = 0;
        int num = 0;
        List<String> cplist = CollUtil.newArrayList(list);
        for (String stri : list) {
            for (Iterator<String> it = cplist.iterator(); it.hasNext(); ) {
                String str = it.next();
                if (StrUtil.isNotBlank(stri) && StrUtil.isNotBlank(str)) {
                    if (str.equals(stri)) {
                        it.remove();
                    } else {
                        total++;
                        double similar = StrUtil.similar(stri, str);
                        if (similar >= minSimilar) {
                            num++;
                        }
                    }
                }
            }
        }
        return total == 0 ? 0 : NumberUtil.div(num, total, 4);
    }


    /**
     * 判断时间数组聚集比例
     * @param dateStrList
     * @param minBetweenDay 相差天数内判断为聚集
     * @return
     */
    public static double getDateGather(List<String> dateStrList, int minBetweenDay) {
        if (CollUtil.isEmpty(dateStrList) || dateStrList.size() <= 1) {
            return 0;
        }
        List<Date> dates = CollUtil.toList();
        for (String dateStr : dateStrList) {
            if (StrUtil.isNotBlank(dateStr)) {
                try {
                    dates.add(DateUtil.parse(dateStr));
                } catch (Exception e) {
                    log.info(e.getMessage());
                }
            }
        }
        if (CollUtil.isEmpty(dates) || dates.size() <= 1) {
            return 0;
        }
        return getGather(dates, minBetweenDay);
    }

    /**
     * 判断时间数组聚集比例
     * @param dateList
     * @param minBetweenDay 相差天数内判断为聚集
     * @return
     */
    public static double getGather(List<Date> dateList, int minBetweenDay) {
        if (CollUtil.isEmpty(dateList) || dateList.size() <= 1) {
            return 0;
        }

        int total = 0;
        int num = 0;
        List<Date> cplist = CollUtil.newArrayList(dateList);
        for (Date datei : dateList) {
            for (Iterator<Date> it = cplist.iterator(); it.hasNext(); ) {
                Date date = it.next();
                if (date.equals(datei)) {
                    it.remove();
                } else {
                    total++;
                    long betweenDay = DateUtil.betweenDay(date, datei, true);
                    System.out.println(betweenDay+"  " +DateUtil.formatDateTime(date)+"   "+DateUtil.formatDateTime(datei));
                    if (betweenDay <= minBetweenDay) {
                        num++;
                    }
                }
            }
        }
        return total == 0 ? 0 : NumberUtil.div(num, total, 4);
    }


}
