package com.yunny.channel.common.exception.handler;

    import com.yunny.channel.common.constant.JumpUrlConstants;
    import com.yunny.channel.common.exception.PermissionException;
    import org.springframework.web.HttpRequestMethodNotSupportedException;
    import org.springframework.web.bind.annotation.ControllerAdvice;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常，页面跳转
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PermissionException.class)
    public ModelAndView handlePermissionException(PermissionException ex) {
        ModelAndView modelAndView = new ModelAndView(ex.getRedirectUrl());
        // 添加日志输出，查看重定向路径
       // System.out.println("Redirecting to: " + ex.getRedirectUrl());
        modelAndView.addObject("errorMsg", ex.getMessage());
        return modelAndView;
    }



    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ModelAndView handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        // 创建一个 ModelAndView 对象，指定登录页面的路径
        ModelAndView modelAndView = new ModelAndView(JumpUrlConstants.CAR_INSURANCE_LOGIN);
        // 可以在这里添加一些额外的信息到 ModelAndView 中，例如错误信息
        modelAndView.addObject("errorMessage", "请求方法不支持，请重新登录。");
        return modelAndView;
    }


}
