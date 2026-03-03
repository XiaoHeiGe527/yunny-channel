package com.yunny.channel.config.aop;

import com.yunny.channel.common.constant.RedisKeyNameConstants;
import com.yunny.channel.common.exception.PermissionException;
import com.yunny.channel.common.exception.ServiceException;
import com.yunny.channel.common.query.VehicleInsuranceQuery;
import com.yunny.channel.common.tools.redis.RedisService;
import com.yunny.channel.service.VehicleInsuranceService;
import com.yunny.channel.service.VehiclesOutwardCardService;
import org.springframework.security.core.AuthenticationException;
import com.yunny.channel.util.JsonStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.Set;

@Slf4j
@Aspect
@Component
public class PermissionAspect {


    @Autowired
    RedisService redisService;

    @Autowired
    VehicleInsuranceService vehicleInsuranceService;

    @Autowired
    VehiclesOutwardCardService vehiclesOutwardCardService;

    // 定义切入点，匹配特定的方法类
    @Pointcut("execution(* com.yunny.channel.controller.VehicleInsuranceController.*(..)) || " +
            "execution(* com.yunny.channel.controller.view.ViewJumpController.*(..)) || " +
            "execution(* com.yunny.channel.controller.VehiclesOutwardCardController.*(..)) || " +
            // 核心修改：匹配EmployeeFileController所有方法，但排除getnameCount方法 与getById方法
            "execution(* com.yunny.channel.controller.EmployeeFileController.*(..)) && !execution(* com.yunny.channel.controller.EmployeeFileController.getNameCount(..))  && !execution(*  com.yunny.channel.controller.EmployeeFileController.getById(..)) || " +
            "execution(* com.yunny.channel.controller.view.RenShiViewJumpController.*(..)) || " +
            "execution(* com.yunny.channel.controller.ChemicalFileImagesController.*(..)) || " +
            "execution(* com.yunny.channel.controller.EmployeeImagesController.*(..)) || " +
            "execution(* com.yunny.channel.controller.SystemRoleController.*(..)) && !execution(* com.yunny.channel.controller.SystemRoleController.listByQuery(..))  && !execution(*  com.yunny.channel.controller.SystemRoleController.getById(..)) || " +

            "execution(* com.yunny.channel.controller.SystemUserController.*(..)) || " +
            "execution(* com.yunny.channel.controller.SystemResourceController.*(..)) && !execution(* com.yunny.channel.controller.SystemResourceController.selectUserResourceList(..))  && !execution(*  com.yunny.channel.controller.SystemResourceController.selectUserValid2LevelResourcesByUserNo(..)) || " +

            "execution(* com.yunny.channel.controller.CompanyVehiclesController.*(..))")
    public void vehicleInsuranceMethods() {
    }

    /**
     * 后期可能在用户登录的时候就把一些频繁且不经常更新的变量存入redis
     * 匹配方法
     */
    @Before("vehicleInsuranceMethods()")
    public void beforeAdvice() {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes()).getRequest();
        // 从request attribute中获取token
        String token = (String) request.getAttribute("token");

        //承保到期数量
        Long expirationReminderCount =  vehicleInsuranceService.expirationReminderCount(new VehicleInsuranceQuery());
        request.setAttribute("expirationReminderCount",expirationReminderCount);

        //卡到期数量
        Long vehiclesOutwardCardCount = vehiclesOutwardCardService.expirationReminderCount();
        request.setAttribute("vehiclesOutwardCardCount",vehiclesOutwardCardCount);


        if (token == null || token.isEmpty()) {
            throw new PermissionException("请求中缺少token属性", "/error/500");
        }
        // 获取请求路径
        String path = request.getRequestURI();
        // 从Redis获取权限URL集合
        String urlJson = redisService.getStringKey(RedisKeyNameConstants.USER_URL_JSON_KEY_PREFIX_REDIS + token);
        if (urlJson == null) {
            throw new PermissionException("无效token或权限数据不存在", "/error/500");
        }

        try {
            Set<String> permittedUrls = JsonStringUtil.jsonToSetString(urlJson);

            if (!permittedUrls.contains(path)) {
                throw new PermissionException("无权访问该资源", "/error/500");
            }

            log.info("用户权限验证通过，访问路径: {}", path);
        } catch (Exception e) {
            log.error("权限验证异常", e);
            throw new PermissionException("权限验证失败", "/error/500");
        }

    }

}
