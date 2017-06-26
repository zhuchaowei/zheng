package com.zheng.common.exception;

/**
 * Created by zhu on 2017/6/26.
 */
public class UpmsException extends RuntimeException{
    public UpmsException() {
    }

    public UpmsException(String message) {
        super(message);
    }

    public UpmsException(Throwable cause) {
        super(cause);
    }

    public UpmsException(String message, Throwable cause) {
        super(message, cause);
    }
}
