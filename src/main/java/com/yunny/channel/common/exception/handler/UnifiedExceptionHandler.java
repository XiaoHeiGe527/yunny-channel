package com.yunny.channel.common.exception.handler;

import com.yunny.channel.common.constant.ExceptionConstants;
import com.yunny.channel.common.enums.ServletResponseEnum;
import com.yunny.channel.common.exception.BaseException;
import com.yunny.channel.common.exception.RpcException;
import com.yunny.channel.common.exception.ServiceException;
import com.yunny.channel.common.result.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * 全局异常处理器
 *@ExceptionHandler和@RestControllerAdvice能够集中异常，使异常处理与业务逻辑分离
 * @author sprainkle
 * @date 2019/5/2
 */
@Slf4j
//@RestControllerAdvice
//@Component
public class UnifiedExceptionHandler {
    /**
     * 生产环境
     */
    private final static String ENV_PROD = "prod";

    /**
     * 当前环境
     */
   // @Value("${spring.profiles.active}")
    private String profile= "test";

    /**
     * 自定义异常
     * @param request exception
     * @return 异常结果
     */
    @ExceptionHandler({
            BaseException.class,
            ServiceException.class,
            RpcException.class
    })
    public BaseResult handleBusinessException(HttpServletRequest request, Exception exception) {
        log.error("errorPath:{}",request.getContextPath() + " " + request.getRequestURI());
        BaseException baseException = (BaseException) exception;
        return BaseResult.failure(baseException.getCode(), baseException.getMessage());
    }

    /**
     * Controller上一层相关异常
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler({
            NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
//            BindException.class,
//            MethodArgumentNotValidException.class
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            MissingServletRequestPartException.class,
            AsyncRequestTimeoutException.class
    })
    public BaseResult handleServletException(Exception e) {
        log.error(e.getMessage(), e);
        int code = ExceptionConstants.RESULT_CODE_BASE_ERROR;
        if (ENV_PROD.equals(profile)) {
            // 当为生产环境, 不适合把具体的异常信息展示给用户, 比如404.
            code = ExceptionConstants.RESULT_CODE_SERVER_ERROR;
            return BaseResult.failure(code, ExceptionConstants.RESULT_NETWORK_ERROR);
        }
        try {
            ServletResponseEnum servletExceptionEnum = ServletResponseEnum.valueOf(e.getClass().getSimpleName());
            code = servletExceptionEnum.getCode();
        } catch (IllegalArgumentException e1) {
            log.error("class [{}] not defined in enum {}", e.getClass().getName(), ServletResponseEnum.class.getName());
        }
        return BaseResult.failure(code, e.getMessage());
    }


    /**
     * 参数绑定异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = BindException.class)
    public BaseResult handleBindException(BindException e) {
        log.error("参数绑定校验异常", e);
        return wrapperBindingResult(e.getBindingResult());
    }

    /**
     * 参数绑定异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public BaseResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数绑定校验异常", e);
        return wrapperBindingResult(e.getBindingResult());
    }

    /**
     * 参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public BaseResult handleConstraintViolationException(ConstraintViolationException e){
        log.error("参数校验异常", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder msg = new StringBuilder();
        for (ConstraintViolation<?> item : violations) {
            msg.append(", ");
            msg.append(item.getMessage() == null ? "" : item.getMessage());
        }
        return BaseResult.failure(ExceptionConstants.RESULT_CODE_ARGUMENT_ERROR, msg.substring(2));
    }

    /**
     * 包装绑定异常结果
     *
     * @param bindingResult 绑定结果
     * @return 异常结果
     */
    private BaseResult wrapperBindingResult(BindingResult bindingResult) {
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : bindingResult.getAllErrors()) {
            msg.append(", ");
            msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());
        }
        return BaseResult.failure(ExceptionConstants.RESULT_CODE_ARGUMENT_ERROR, msg.substring(2));
    }

    /**
     * 未定义异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = Throwable.class)
    public BaseResult handleException(Exception e) {
        log.error(e.getMessage(), e);
        if (ENV_PROD.equals(profile)) {
            // 当为生产环境, 不适合把具体的异常信息展示给用户, 比如数据库异常信息.
            return BaseResult.failure(ExceptionConstants.RESULT_CODE_SERVER_ERROR, ExceptionConstants.RESULT_NETWORK_ERROR);
        }
        return BaseResult.failure(ExceptionConstants.RESULT_CODE_BASE_ERROR, e.getMessage());
    }

}
