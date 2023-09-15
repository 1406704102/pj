package com.pangjie.pjException.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PJException {

    private Integer status = 400;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private String message;

    private PJException() {
        timestamp = LocalDateTime.now();
    }

    public static PJException error(String message) {
        PJException apiError = new PJException();
        apiError.setMessage(message);
        return apiError;
    }

    public static PJException error(Integer status, String message) {
        PJException pjException = new PJException();
        pjException.setStatus(status);
        pjException.setMessage(message);
        return pjException;
    }

}
