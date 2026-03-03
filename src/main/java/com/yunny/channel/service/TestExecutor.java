package com.yunny.channel.service;

import com.yunny.channel.common.result.BaseResult;

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.Future;

public interface TestExecutor {

    /**
     * 测试多线程执行器
     * @param str
     * @return
     * @throws URISyntaxException
     */
    public Future<BaseResult> testExecutor(String str) throws URISyntaxException;
}
