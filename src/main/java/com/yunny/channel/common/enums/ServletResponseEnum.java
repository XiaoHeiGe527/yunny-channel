package com.yunny.channel.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

/**
 * 异常类与http status对照关系
 *
 * @author hexin
 * @date 2019/5/2
 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
 */
@Getter
@AllArgsConstructor
public enum ServletResponseEnum {

    /**
     *
     */
    MethodArgumentNotValidException(HttpServletResponse.SC_BAD_REQUEST, ""),
    /**
     *
     */
    MethodArgumentTypeMismatchException(HttpServletResponse.SC_BAD_REQUEST, ""),
    /**
     *
     */
    MissingServletRequestPartException(HttpServletResponse.SC_BAD_REQUEST, ""),
    /**
     *
     */
    MissingPathVariableException(HttpServletResponse.SC_BAD_REQUEST, ""),
    /**
     *
     */
    BindException(HttpServletResponse.SC_BAD_REQUEST, ""),
    /**
     *
     */
    MissingServletRequestParameterException(HttpServletResponse.SC_BAD_REQUEST, ""),
    TypeMismatchException(HttpServletResponse.SC_BAD_REQUEST, ""),
    /**
     *
     */
    ServletRequestBindingException(HttpServletResponse.SC_BAD_REQUEST, ""),
    /**
     *
     */
    HttpMessageNotReadableException(HttpServletResponse.SC_BAD_REQUEST, ""),
    /**
     *
     */
    NoHandlerFoundException(HttpServletResponse.SC_NOT_FOUND, ""),
    /**
     *
     */
    NoSuchRequestHandlingMethodException(HttpServletResponse.SC_NOT_FOUND, ""),
    /**
     *
     */
    HttpRequestMethodNotSupportedException(HttpServletResponse.SC_METHOD_NOT_ALLOWED, ""),
    /**
     *
     */
    HttpMediaTypeNotAcceptableException(HttpServletResponse.SC_NOT_ACCEPTABLE, ""),
    /**
     *
     */
    HttpMediaTypeNotSupportedException(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, ""),
    /**
     *
     */
    ConversionNotSupportedException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ""),
    /**
     *
     */
    HttpMessageNotWritableException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ""),
    /**
     * 异步请求超时
     */
    AsyncRequestTimeoutException(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "")
    ;

    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回信息，直接读取异常的message
     */
    private String message;
}
