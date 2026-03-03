package com.yunny.channel.controller;

import com.yunny.channel.common.interfaces.SearchGroup;
import com.yunny.channel.common.interfaces.annotate.LogExecutionTime;
import com.yunny.channel.common.query.YunnyLoginQuery;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.tools.redis.RedisService;
import com.yunny.channel.common.util.CheckImage;
import com.yunny.channel.service.LoginService;
import com.yunny.channel.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.yunny.channel.common.constant.CommonConstant.platform_WEB;
import static com.yunny.channel.common.enums.UserCodeEnum.USER_OR_PASSWORD_IS_INCORRECT;
import static com.yunny.channel.common.constant.ExceptionConstants.EXCEPTION;


@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    private HttpServletRequest httpServletRequest;


    @Autowired
    private RedisService redisService;

    /**
     * IP请求频率
     */
    @Value("${ipConfig.rate}")
    private String ipConfigRate;

    /**
     * 频率设置多少秒
     */
    @Value("${ipConfig.seconds}")
    private String ipConfigSeconds;


    /**
     * 黑名单时间
     */
    @Value("${ipConfig.blacklistTime}")
    private String blacklistTime;


    /**
     * @param httpServletRequest AOP记录日志用！
     */
    @PostMapping("/yunny/login")
    @LogExecutionTime(value = "注解的value是123")
    public BaseResult yunnyLogin(
        @RequestBody @Validated({SearchGroup.class}) YunnyLoginQuery command,
        HttpServletRequest httpServletRequest) {

        command.setPlatform(platform_WEB);//登录平台默认位WEB
        try {
            /**
             * 真实数据库密码校验
             */
            if (!StringUtil.isEmpty(command.getPassword())) {
                return loginService.yunnyLogin(command);
            } else {

                return BaseResult.failure(USER_OR_PASSWORD_IS_INCORRECT.getCode(), USER_OR_PASSWORD_IS_INCORRECT.getMessage());
            }

        } catch (Exception e) {
            log.error("登录异常：", e);
            return BaseResult.failure(EXCEPTION, e.getMessage());
        }
    }

    /**
     * 请求redis校验IP
     */
//    public BaseResult checkIpAddr(String ipAddress) {
//
//        /**
//         * 以IP为KEY
//         */
//        String redisKey = RedisKeyNameConstants.LIMIT_IP + ipAddress;
//        /**
//         * 查看我们黑名单IP KEY
//         */
//        String blackRedisKey = RedisKeyNameConstants.LIMIT_BLACK_IP + ipAddress;
//
//        CheckIpQuery query = new CheckIpQuery();
//        query.setIpLimitKey(redisKey);
//        query.setIpLimiBlackKey(blackRedisKey);
//        query.setIpAddress(ipAddress);
//        query.setRate(Long.valueOf(ipConfigRate));
//        query.setSeconds(Long.valueOf(ipConfigSeconds));
//        query.setBlackTime(Long.valueOf(blacklistTime));
//        return redisService.checkIpAddr(query);
//
//    }


    /**
     * @param @return 参数说明
     * @return BaseRestResult 返回类型
     * @Description: 生成滑块拼图验证码
     */
    @RequestMapping(value = "/getImageVerifyCode", method = RequestMethod.GET, produces = {
        "application/json;charset=UTF-8"})
    public BaseResult getImageVerifyCode(HttpServletRequest httpServletRequest) {
        Map<String, Object> resultMap = new HashMap<>();
//        //读取本地路径下的图片,随机选一条
//        File file = new File(this.getClass().getResource("/image").getPath());
//        File[] files = file.listFiles();
//        int n = new Random().nextInt(files.length);
//        File imageUrl = files[n];
//        CheckImage.createImage(imageUrl, resultMap);

        //读取网络图片
        CheckImage.createImage(
            "http://hbimg.b0.upaiyun.com/7986d66f29bfeb6015aaaec33d33fcd1d875ca16316f-2bMHNG_fw658",
            resultMap);



        httpServletRequest.setAttribute("xWidth", resultMap.get("xWidth"));
        resultMap.remove("xWidth");
        resultMap.put("errcode", 0);
        resultMap.put("errmsg", "success");
        return BaseResult.success(resultMap);
    }


    /**
     * 校验滑块拼图验证码
     *
     * @param moveLength 移动距离
     * @return BaseRestResult 返回类型
     * @Description: 生成图形验证码
     */
    @RequestMapping(value = "/verifyImageCode", method = RequestMethod.GET, produces = {
        "application/json;charset=UTF-8"})
    public BaseResult verifyImageCode(@RequestParam(value = "moveLength") String moveLength,
        HttpServletRequest httpServletRequest) {
        Double dMoveLength = Double.valueOf(moveLength);
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Integer xWidth = (Integer) httpServletRequest.getAttribute("xWidth");
            if (xWidth == null) {
                resultMap.put("errcode", 1);
                resultMap.put("errmsg", "验证过期，请重试");
                return BaseResult.success(resultMap);
            }
            if (Math.abs(xWidth - dMoveLength) > 10) {
                resultMap.put("errcode", 1);
                resultMap.put("errmsg", "验证不通过");
            } else {
                resultMap.put("errcode", 0);
                resultMap.put("errmsg", "验证通过");
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            httpServletRequest.removeAttribute("xWidth");
        }
        return BaseResult.success(resultMap);

    }

//<img src="https://img-blog.csdnimg.cn/2022010700031595841.png" alt="抠图">
//<img src="https://img-blog.csdnimg.cn/2022010700031595841.png" alt="带抠图阴影的原图">

//
//    /**
//     * 短信登錄
//     * @param command
//     * @param httpServletRequest
//     * @return
//     */
//    @PostMapping("/yunny/nodeRecodeLogin")
//    public BaseResult nodeRecodeLogin(
//        @RequestBody @Validated({OtherGroup.class}) YunnyLoginQuery command,
//        HttpServletRequest httpServletRequest) {
//        return BaseResult.success(loginService.nodeRecodeLogin(command));
//    }



    /**
     * 退出登录
     * @return
     */
    @PostMapping("/yunny/logout")
    public BaseResult logout(HttpServletRequest request,@RequestAttribute("userNo") String userNo){

        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            token = httpServletRequest.getParameter("token");
        }

        loginService.logout(token,userNo);
        request.removeAttribute("token");
        return BaseResult.success();
    }
}
