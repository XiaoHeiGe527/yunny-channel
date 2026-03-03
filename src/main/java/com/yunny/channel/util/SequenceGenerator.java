package com.yunny.channel.util;

/**
 * @ClassName SequenceGenerator
 * @Description TODO
 * @Author sunfuwei521@qq.com
 * @Date 2025/11/1 14:25
 */

import com.yunny.channel.common.tools.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 计划单号生成工具类（每天从001递增，线程安全）
 */
@Component
public class SequenceGenerator {



    // 日期格式化器（用于yyyyMMdd格式，如20251101）
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    // 当前日期字符串（用于判断是否跨天）
    private volatile String currentDate;
    // 原子类序号（确保多线程环境下安全自增）
    private AtomicInteger sequence;

    // 初始化：默认以当前日期和序号1开始
    public SequenceGenerator() {
        this.currentDate = getCurrentDateStr();
        this.sequence = new AtomicInteger(1);
    }

    /**
     * 获取当天的3位序号（格式：001~999）
     * 跨天时自动重置为001
     */
    public String getDailySequence() {

        String today = getCurrentDateStr();

        // 跨天判断：如果当前日期与系统日期不一致，重置序号（双重检查锁保证线程安全）
        if (!today.equals(currentDate)) {
            synchronized (this) {
                if (!today.equals(currentDate)) {
                    currentDate = today;
                    sequence.set(1); // 重置序号为1
                }
            }
        }

        // 获取自增序号并格式化为3位（不足补0）
        int seq = sequence.getAndIncrement();
        if (seq > 999) {
            throw new RuntimeException("当天计划数量已超过999条，无法生成更多单号");
        }
        return String.format("%03d", seq);
    }

    /**
     * 获取当前日期的字符串（yyyyMMdd）
     */
    private String getCurrentDateStr() {



        return LocalDate.now().format(DATE_FORMATTER);
    }
}
