package com.yunny.channel.controller;

import com.yunny.channel.common.interfaces.SearchGroup;
import com.yunny.channel.common.interfaces.UpdateGroup;
import com.yunny.channel.common.interfaces.annotate.LogExecutionTime;
import com.yunny.channel.common.query.SystemUserQuery;
import com.yunny.channel.common.query.YunnyLoginQuery;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.vo.LoginDataVO;
import com.yunny.channel.common.vo.SystemUserVO;
import com.yunny.channel.service.SystemLoginService;
import com.yunny.channel.service.SystemUserService;
import com.yunny.channel.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import static com.yunny.channel.common.constant.ExceptionConstants.EXCEPTION;
import static com.yunny.channel.common.enums.UserCodeEnum.USER_OR_PASSWORD_IS_INCORRECT;

@Slf4j
@RestController
@RequestMapping("/sys")
public class SystemLoginController {

    @Autowired
    SystemLoginService systemLoginService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private SystemUserService systemUserService;

    /**
     * AOP记录日志用！
     */
    @PostMapping("/login")
    @LogExecutionTime(value = "注解的value是123")
    public BaseResult yunnyLogin(
            @RequestBody @Validated({SearchGroup.class}) YunnyLoginQuery command) {
        command.setPlatform("web");//登录平台默认位WEB
        try {
            /**
             * 真实数据库密码校验
             */
            if (!StringUtil.isEmpty(command.getPassword())) {
                return systemLoginService.yunnyLogin(command);

            } else {

                return BaseResult
                        .failure(USER_OR_PASSWORD_IS_INCORRECT.getCode(), USER_OR_PASSWORD_IS_INCORRECT.getMessage());
            }

        } catch (Exception e) {
            log.error("登录异常：", e);
            return BaseResult.failure(EXCEPTION, e.getMessage());
        }
    }

    /**
     * 退出登录
     *
     * @return
     */
    @PostMapping("/logout")
    public BaseResult logout(HttpServletRequest request,
                             @RequestParam("userNo") @NotNull String userNo) {

        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            token = httpServletRequest.getParameter("token");
        }
        systemLoginService.logout(token, userNo);
        request.removeAttribute("token");
        return BaseResult.success();
    }


    /**
     * 修改密码
     *
     * @param
     * @return
     */
    @PostMapping("/changePassword")
    public BaseResult changePassword(@RequestBody @Validated({UpdateGroup.class}) SystemUserQuery systemUserQuery) {
        return systemUserService.changePassword(systemUserQuery);
    }

}
