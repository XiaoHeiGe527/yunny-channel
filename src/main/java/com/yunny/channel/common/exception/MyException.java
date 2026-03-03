package com.yunny.channel.common.exception;

import com.yunny.channel.common.enums.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 异常代码
     */
    private Integer code;
    /**
     * 异常信息
     */
    private String message;

    public MyException(ResultEnum resultEnum) {
        super();
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    public MyException(Integer code, String message) {
        super();
        this.code = code != null ? code : ResultEnum.FAILURE.getCode();
        this.message = message;
    }

    public MyException(String message) {
        super();
        this.code = ResultEnum.FAILURE.getCode();
        this.message = message;
    }

    public MyException(Exception ex) {
        super();
        if (ex instanceof MyException) {
            MyException e = (MyException) ex;
            this.code = e.getCode();
            this.message = e.getMessage();
        } else {
            this.code = ResultEnum.FAILURE.getCode();
            this.message = ex.getMessage();
        }
    }

}
