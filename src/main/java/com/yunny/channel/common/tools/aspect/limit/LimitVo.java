package com.yunny.channel.common.tools.aspect.limit;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LimitVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 访问次数
     */
    private int count;

    /**
     * 有效期内首次访问时间
     */
    private Date firstTime;

    /**
     * 限制期间，单位秒
     */
    private long period;

    /**
     * 获取剩余时长（秒）
     *
     * @return
     */
    public long getExpire() {
        long expire = period - DateUtil.between(new Date(), firstTime, DateUnit.SECOND) / 1000;
        return expire > 0 ? expire : 0;
    }

}
