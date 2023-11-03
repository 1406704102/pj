package com.pangjie.pjException;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * 全局业务异常类
 *
 * @author Chopper
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 3447728300174142127L;

    public static final String DEFAULT_MESSAGE = "网络错误，请稍后重试！";

    private Integer status = BAD_REQUEST.value();

    /**
     * 异常消息
     */
    private String msg = DEFAULT_MESSAGE;

    /**
     * 错误码
     */
//    private ResultCode resultCode;

//    public ServiceException(String msg) {
//        this.resultCode = ResultCode.ERROR;
//        this.msg = msg;
//    }

    public BadRequestException() {
        super();
    }

//    public ServiceException(ResultCode resultCode) {
//        this.resultCode = resultCode;
//    }
//
//    public ServiceException(ResultCode resultCode, String message) {
//        this.resultCode = resultCode;
//        this.msg = message;
//    }

}
