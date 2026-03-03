package com.yunny.channel.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yunny.channel.common.constant.ExceptionConstants;
import com.yunny.channel.common.constant.UserBaseInfoConstant;
import com.yunny.channel.common.dto.RedisLockDTO;
import com.yunny.channel.common.entity.UserBaseInfoDO;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.query.LoginUserQuery;
import com.yunny.channel.common.query.TestMqQuery;
import com.yunny.channel.common.query.UserBaseInfoQuery;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.tools.redis.RedisService;
import com.yunny.channel.common.vo.UserBaseInfoVo;
import com.yunny.channel.common.util.excel.constant.UserBaseInfoExcel;
import com.yunny.channel.mapper.UserBaseInfoMapper;
import com.yunny.channel.rabbitmq.sender.TestSender;
import com.yunny.channel.service.TestExecutor;
import com.yunny.channel.service.UserBaseInfoService;
import com.yunny.channel.util.RedisLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
/**
 * Created by Fe
 * Service注解给类起名称name 注入的时候可以根据name注入
 */
@Slf4j
@Service("UserBaseInfoService1")
public class UserBaseInfoServiceImpl implements UserBaseInfoService {

    @Autowired
    UserBaseInfoMapper userBaseInfoMapper;

    @Autowired
    RedisService redisService;


    @Autowired
    private RedisLockUtil redisLockUtil;

    @Autowired
    TestSender testSender;

    @Autowired
    TestExecutor testExecutor;

    private final String REDIS_KEY = "CESHI:RDIS:TEST";

    @Override
    public List<UserBaseInfoVo> selectUserBaseInfoList(UserBaseInfoQuery userBaseInfoQuery) {

        List<UserBaseInfoDO> ubiDoList = userBaseInfoMapper.selectUserBaseInfoDOList(userBaseInfoQuery);

        //向redis  写入值
        redisService.setKeyAndValueAndExpireTime(REDIS_KEY,"测试redis接口是否可用",1L);

        List<UserBaseInfoVo> ubiVo = new ArrayList<>();
        ubiDoList.forEach( p -> {
            UserBaseInfoVo vo = new UserBaseInfoVo();
            vo.setMobile(p.getMobile());
            vo.setPassword(p.getPassword());
            vo.setState(p.getState());
            vo.setUserEmail(p.getUserEmail());
            vo.setUserName(p.getUserName());
            vo.setUserNickName(p.getUserNickName());
            vo.setUserPicture(p.getUserPicture());
            vo.setUserNo(p.getUserNo());
            vo.setId(p.getId());
            ubiVo.add(vo);
        });

        return ubiVo;
    }

    @Override
    public  List<UserBaseInfoVo> queryUserBaseInfoDOList(LoginUserQuery query) {
        List<UserBaseInfoDO> ubiDoList = userBaseInfoMapper.queryUserBaseInfoDOList(query);

        List<UserBaseInfoVo> ubiVoList = new ArrayList<>();
        if(CollectionUtils.isEmpty(ubiDoList)){

           return null;
        }

        ubiDoList.forEach( p -> {
            UserBaseInfoVo vo = new UserBaseInfoVo();
            vo.setMobile(p.getMobile());
            vo.setPassword(p.getPassword());
            vo.setState(p.getState());
            vo.setUserEmail(p.getUserEmail());
            vo.setUserName(p.getUserName());
            vo.setUserNickName(p.getUserNickName());
            vo.setUserPicture(p.getUserPicture());
            vo.setUserNo(p.getUserNo());
            vo.setId(p.getId());
            ubiVoList.add(vo);
        });

        return ubiVoList;
    }

    @Override
    public CommonPager<UserBaseInfoVo> listByQuery(UserBaseInfoQuery userBaseInfoQuery) {
        PageParameter pageParameter = userBaseInfoQuery.getPageParameter();
        return new CommonPager<>(
                new PageParameter(pageParameter.getCurrentPage(), pageParameter.getPageSize(),
                        userBaseInfoMapper.countByQuery(userBaseInfoQuery)),
                userBaseInfoMapper.listByQuery(userBaseInfoQuery).stream()
                        .map(p -> {
                            UserBaseInfoVo vo = new UserBaseInfoVo();
                            vo.setMobile(p.getMobile());
                            vo.setPassword(p.getPassword());
                            vo.setState(p.getState());
                            vo.setUserEmail(p.getUserEmail());
                            vo.setUserName(p.getUserName());
                            vo.setUserNickName(p.getUserNickName());
                            vo.setUserPicture(p.getUserPicture());
                            vo.setUserNo(p.getUserNo());
                            vo.setId(p.getId());
                            return vo;
                        }).collect(Collectors.toList()));
    }


    @Override
    public BaseResult testSendMq(String userNo) {

        /**
         * 锁住用户号防止多次操作
         */
        String key = UserBaseInfoConstant.LOCK_USER_NO +userNo;
        String value = UUID.randomUUID().toString();
        int expireTime = new Random().nextInt(2) + 3;
        RedisLockDTO redisLockDTO = new RedisLockDTO();
        redisLockDTO.setKey(key);
        redisLockDTO.setValue(value);
        redisLockDTO.setExpireTime(expireTime);
        boolean lock = redisLockUtil.getRedisLock(redisLockDTO);
        if (!lock) {
            log.error("操作异常 ，不可重复操作，用户号：[{}]",userNo);
            return BaseResult.failure(ExceptionConstants.RESULT_CODE_SERVER_ERROR,"操作异常 ，不可重复操作");
        }

        try{

            //请求redis获取值
           String rediValue = redisService.getStringKey(REDIS_KEY);

            //向MQ发送消息

            this.assignConvertAndSend(new TestMqQuery("用户号:"+userNo+"redis获取的值:"+rediValue));


            return BaseResult.success("请求完成，已向MQ发送消息！");


        }catch (Exception e) {

            log.info("操作异常，异常信息:-[{}]-",e);
            log.error("操作异常！异常信息:-[{}]-",e.getMessage(), e);
            return BaseResult.failure(ExceptionConstants.RESULT_CODE_SERVER_ERROR,e.getMessage());

        } finally {
            /**
             * 解锁
             */
            redisService.releaseLock(redisLockDTO);

        }
    }

    @Override
    public BaseResult testExecutorStr(int count) {

        Integer successCount = 0;
        for(int i = 0 ;i<=count;i++){

            try {
                Future<BaseResult> future = testExecutor.testExecutor("这是第：[{"+count+"}],条数据！");
                BaseResult result = (BaseResult) future.get();
                if (result.getCode() != ExceptionConstants.RESULT_CODE_SUCCESS || result.getData() == null) {
                    log.info("code:[{}],msg:[{}]",result.getCode(),result.getMessage());
                    continue;
                }else {

                    successCount++;
                }


            } catch (Exception e) {
                log.error("处理数据发送错误！msg:[{}]",e.getMessage());
                continue;
            }
        }
        return BaseResult.success(successCount);
    }

    /**
     * 导出Excel
     * @param query
     * @return
     */
    @Override
    public List<UserBaseInfoExcel> findList(UserBaseInfoQuery query) {
        List<UserBaseInfoExcel> retentionRateExcel = userBaseInfoMapper.listByQuery(query)
                .stream()
                .map(UserBaseInfoExcel::builder)
                .collect(Collectors.toList());
        return retentionRateExcel;
    }

    private void  assignConvertAndSend( TestMqQuery aavq){
        String assignVmStr = JSONObject.toJSONString(aavq);
        //插入mq 调用分配虚拟机接口
        testSender.testSend(assignVmStr);
    }

}
