package com.yunny.channel.service.impl;

import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.service.TestExecutor;
import com.yunny.channel.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.Future;

@Service
@Slf4j
public class TestExecutorImpl implements TestExecutor {


    /**
     * 多线程执行类
     *    @Async("配置线程池中Bean")
     * @param str
     * @return
     * @throws URISyntaxException
     */
    @Async("testExecutor")
    @Override
    public Future<BaseResult> testExecutor(String str) throws URISyntaxException {

        if(StringUtil.isEmpty(str)){
            return new AsyncResult<>(BaseResult.success(0));
        }

        log.info("输出str:[{}],线程名称：[{}]", str,Thread.currentThread().getName());

        return new AsyncResult<>(BaseResult.success(str));

    }
}
