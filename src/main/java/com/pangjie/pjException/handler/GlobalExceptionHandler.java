package com.pangjie.pjException.handler;


import io.netty.util.internal.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * @param
     * @return
     * @Author PangJie___
     * @Description 处理未知异常
     * @Date 17:31 2023/9/15
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<PJException> handleException(Throwable throwable) {
        log.error(ThrowableUtil.stackTraceToString(throwable));
        return buildResponseEntity(PJException.error(throwable.getMessage()));
    }



    private ResponseEntity<PJException> buildResponseEntity(PJException serviceException) {
        return new ResponseEntity<>(serviceException, HttpStatus.valueOf(serviceException.getStatus()));
    }
}
