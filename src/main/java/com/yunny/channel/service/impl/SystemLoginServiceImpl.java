package com.yunny.channel.service.impl;

import com.alibaba.fastjson.JSON;
import com.yunny.channel.common.constant.BusinessCodeConstants;
import com.yunny.channel.common.constant.ExceptionConstants;
import com.yunny.channel.common.constant.RedisKeyNameConstants;
import com.yunny.channel.common.query.LoginUserQuery;
import com.yunny.channel.common.query.YunnyLoginQuery;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.tools.redis.RedisService;
import com.yunny.channel.common.util.JwtUtil;
import com.yunny.channel.common.util.MD5;
import com.yunny.channel.common.util.StringUtil;
import com.yunny.channel.common.vo.LoginDataVO;
import com.yunny.channel.common.vo.SystemUserVO;
import com.yunny.channel.service.SystemLoginService;
import com.yunny.channel.service.SystemResourceService;
import com.yunny.channel.service.SystemUserService;
import com.yunny.channel.util.JsonStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

import static com.yunny.channel.common.constant.CommonConstant.platform_WEB;
import static com.yunny.channel.common.enums.UserCodeEnum.*;

@Slf4j @Service public class SystemLoginServiceImpl implements SystemLoginService {

    @Autowired RedisService redisService;

    @Autowired SystemUserService systemUserService;

    @Autowired SystemResourceService systemResourceService;

    @Override public BaseResult yunnyLogin(YunnyLoginQuery command) {

        /**
         * 校验用户
         */
        LoginUserQuery query =
            LoginUserQuery.builder().loginName(command.getAccount()).password(command.getPassword()).build();
        BaseResult<SystemUserVO> verifyResult = this.verifyYunnyAccount(query);
        if (verifyResult.getCode() != ExceptionConstants.RESULT_CODE_SUCCESS) {
            throw new RuntimeException(verifyResult.getMessage());
        }

        ///清除该用户的token delToken
        /**
         * 生成并存储token
         */
        SystemUserVO userVo = verifyResult.getData();
        String token =
            JwtUtil.generateJwtToken(userVo.getUserNo(), String.valueOf(userVo.getId()), BusinessCodeConstants.SALT);
        //删除该登录平台的历史token
        this.delOldToken(userVo, command.getPlatform());

        redisService.addToken(RedisKeyNameConstants.USER_TOKEN_KEY_PREFIX_REDIS + token, JSON.toJSONString(userVo));
        //单点登录标识
        redisService.addToken(this.getLoginSign(userVo, command.getPlatform()),
            RedisKeyNameConstants.USER_TOKEN_KEY_PREFIX_REDIS + token);
        LoginDataVO loginData = LoginDataVO.builder().token(token).userNo(userVo.getUserNo()).build();

        String urlJson = systemResourceService.selectUserResourceListJSON(userVo.getUserNo());
        log.info("================================urlJson:[{}]", urlJson);
        redisService.addToken(RedisKeyNameConstants.USER_URL_JSON_KEY_PREFIX_REDIS + token, urlJson);
        return BaseResult.success(loginData);
    }

    @Override public void logout(String token, String userNo) {
        SystemUserVO userVo = systemUserService.getByUserNo(userNo);
        //删除该登录平台的历史token
        this.delOldToken(userVo, platform_WEB);
    }

    private BaseResult delOldToken(SystemUserVO userVo, String platform) {
        String tokenStr = redisService.getStringKey(this.getLoginSign(userVo, platform));
        if (StringUtil.isEmpty(tokenStr)) {
            return BaseResult.success();
        }
        redisService.delToken(tokenStr);
        redisService.delToken(this.getLoginSign(userVo, platform));

        return BaseResult.success();
    }

    private String getLoginSign(SystemUserVO userVo, String platform) {
        return RedisKeyNameConstants.LOGIN_SIGN + platform + userVo.getUserNo();
    }

    /**
     * 校验登录用户
     *
     * @param query
     * @return
     */
    public BaseResult<SystemUserVO> verifyYunnyAccount(LoginUserQuery query) {

        String webPassword = query.getPassword();
        query.setPassword("");
        List<SystemUserVO> userList = systemUserService.queryUserBaseInfoDOList(query);

        if (CollectionUtils.isEmpty(userList)) {

            return BaseResult.failure(SYSTEM_USER_DOES_NOT_EXIST.getCode(), SYSTEM_USER_DOES_NOT_EXIST.getMessage());
        }

        /**
         * 用户不存在或者大于1
         */
        if (userList.size() > 1) {

            return BaseResult.failure(SYSTEM_USER_IS_ILLEGAL.getCode(), SYSTEM_USER_IS_ILLEGAL.getMessage());
        }

        SystemUserVO user = userList.get(0);

        /**
         * 用户是否正常状态
         */
        if (BusinessCodeConstants.USER_STATUS_EFFECTIVE != user.getState()) {
            return BaseResult.failure(USER_DISABLED.getCode(), USER_DISABLED.getMessage());
        }

        /**
         * 校验用户密码
         */
        log.info("md5pass==[{}]", MD5.md5(webPassword));
        if (!user.getPassword().equals(MD5.md5(webPassword))) {
            return BaseResult
                .failure(USER_OR_PASSWORD_IS_INCORRECT.getCode(), USER_OR_PASSWORD_IS_INCORRECT.getMessage());
        }

        return BaseResult.success(user);
    }

}